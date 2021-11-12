package com.meeweel.anilist.viewmodel

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
import java.lang.Exception

class AnimeSynchronizer(
    private val aniApi: AnimeApi,
    private val repository: LocalRepository = LocalRepositoryImpl(App.getEntityDao()),
    private val picMaker: ImageMaker = ImageMaker()
) {
    private val compositeDisposable = CompositeDisposable()
    private val list: MutableList<AnimeResponse> = mutableListOf()

    fun synchronize() {
        var actualQuantity = 0
        val localQuantity = repository.getQuantity()
        compositeDisposable.add(aniApi.getQuantity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Toast.makeText(getContext(), "Hi", Toast.LENGTH_SHORT).show()
                actualQuantity = it.id
                ifIf(actualQuantity, localQuantity)
            }, {

            }))
    }

    private fun ifIf(actualQuantity: Int, localQuantity: Int) {
        Toast.makeText(getContext(), "ifIf()", Toast.LENGTH_SHORT).show()
        if (actualQuantity > localQuantity) {
            for (i in (localQuantity + 1)..actualQuantity) {
                compositeDisposable.add(aniApi.getAnime(i)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        picMaker.savePictureToDirectory(it.image, getImageName(it.image))
                        Toast.makeText(getContext(), "Hi", Toast.LENGTH_SHORT).show()
                        list.add(it)
                        if (i == actualQuantity) insert()
                    }, {

                    }))
            }
        }
    }

    fun insert() {
        Toast.makeText(getContext(), "insert()", Toast.LENGTH_SHORT).show()
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