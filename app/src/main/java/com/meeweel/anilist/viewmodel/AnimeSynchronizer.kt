package com.meeweel.anilist.viewmodel

import android.os.Handler
import android.widget.Toast
import com.meeweel.anilist.api.AnimeApi
import com.meeweel.anilist.api.AnimeResponse
import com.meeweel.anilist.model.repository.LocalRepository
import com.meeweel.anilist.model.repository.LocalRepositoryImpl
import com.meeweel.anilist.model.room.App
import com.meeweel.anilist.model.room.entityes.Entity
import com.meeweel.anilist.viewmodel.Changing.getContext
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AnimeSynchronizer(
    private val aniApi: AnimeApi,
    private val repository: LocalRepository = LocalRepositoryImpl(App.getEntityDao()),
    private val picMaker: ImageMaker = ImageMaker()
) {
    val handler: Handler = Handler(getContext().mainLooper)
    var actualQuantity = 0
    var localQuantity = repository.getQuantity()
    private val compositeDisposable = CompositeDisposable()
    private val list: MutableList<AnimeResponse> = mutableListOf()

    fun synchronize() {
        compositeDisposable.add(aniApi.getQuantity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Toast.makeText(getContext(), "Начало синхронизации", Toast.LENGTH_SHORT).show()
                actualQuantity = it.id
                ifIf(actualQuantity, localQuantity)
            }, {

            }))
    }

    private fun ifIf(actualQuantity: Int, localQuantity: Int) {
        Toast.makeText(getContext(), "Проверка актуальности данных", Toast.LENGTH_SHORT).show()
        if (actualQuantity > localQuantity) {
            Toast.makeText(getContext(), "Найдены новые аниме", Toast.LENGTH_SHORT).show()
            controller()
            for (i in (localQuantity + 1)..actualQuantity) {
                compositeDisposable.add(aniApi.getAnime(i)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        picMaker.savePictureToDirectory(it.image, getImageName(it.image))
                        Toast.makeText(getContext(), "Новое аниме id=${it.id}", Toast.LENGTH_SHORT).show()
                        list.add(it)
                    }, {
                        reLoad(i)
                    }))
            }
        } else {
            Toast.makeText(getContext(), "Данные актуальны", Toast.LENGTH_SHORT).show()
        }
    }
    fun controller() {
        Thread {
            while (!(list.size == actualQuantity - localQuantity)) {
                Thread.sleep(500)
            }
            runOnUiThread {
                insert()
            }
        }.start()
    }

    private fun runOnUiThread(r: Runnable) {
        handler.post(r)
    }

    fun reLoad(i: Int) {
        compositeDisposable.add(aniApi.getAnime(i)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                picMaker.savePictureToDirectory(it.image, getImageName(it.image))
                Toast.makeText(getContext(), "Новое аниме id=${it.id}", Toast.LENGTH_SHORT).show()
                list.add(it)
            }, {
                reLoad(i)
            }))
    }
    fun insert() {
        Toast.makeText(getContext(), "Сохранение новых аниме", Toast.LENGTH_SHORT).show()
        for (item in list) {
                Thread.sleep(1000)
                repository.insertLocalEntity(
                    Entity(
                        item.id, item.ruTitle, item.enTitle, item.originalTitle, item.ruDescription,
                        item.enDescription, getImageName(item.image), item.data, item.ruGenre, item.enGenre,
                        item.author, item.ageRating, getRating(item), item.seriesQuantity, 0, 1
                    )
                )
        }
        Toast.makeText(getContext(), "Сохранение завершено", Toast.LENGTH_SHORT).show()
    }
    private fun getRating(anime: AnimeResponse): Int {
        return try {
            (anime.rating2 * 25 + anime.rating3 * 50 + anime.rating4 * 75 +
                    anime.rating5 * 100) / (anime.rating1 + anime.rating2 +
                    anime.rating3 + anime.rating4 + anime.rating5)
        } catch (e: Exception) {
            0
        }
    }
    private fun getImageName(title: String) : String {
        return title
            .replace("https://anilist.pserver.ru/pictures/", "")
            .replace(".jpg", "")
            .replace(".png", "")

        // return title
        //    .replace(" ", "")
        //    .replace(":", "").replace(";", "")
        //    .replace("(", "").replace(")", "")
        //    .replace("/", "").replace(".", "")
        //    .replace(",", "").replace("'", "")
        //    .replace("\"", "").replace("?", "")
        //    .replace("!", "").replace("&", "").lowercase()
    }
}