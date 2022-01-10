package com.meeweel.anilist.viewmodel

import android.os.Handler
import com.google.android.material.snackbar.Snackbar
import com.meeweel.anilist.api.AnimeApi
import com.meeweel.anilist.api.AnimeResponse
import com.meeweel.anilist.databinding.ActivityMainBinding
import com.meeweel.anilist.model.repository.LocalRepository
import com.meeweel.anilist.model.repository.LocalRepositoryImpl
import com.meeweel.anilist.model.room.App
import com.meeweel.anilist.model.room.convertResponseListToEntityList
import com.meeweel.anilist.model.room.convertResponseToEntity
import com.meeweel.anilist.model.room.entityes.Entity
import com.meeweel.anilist.viewmodel.Changing.getContext
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AnimeSynchronizer(
    private val aniApi: AnimeApi,
    private val bind: ActivityMainBinding,
    private val repository: LocalRepository = LocalRepositoryImpl(App.getEntityDao()),
//    private val picMaker: ImageMaker = ImageMaker()
) {
    private val handler: Handler =
        Handler(getContext().mainLooper) // Нужен для запуска главного потока
    private var actualQuantity = 0
    private var localQuantity = repository.getQuantity()
    private val compositeDisposable = CompositeDisposable()

    fun synchronize() {
        compositeDisposable.add(
            aniApi.getQuantity()  // Получение количества аниме на сервере
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    actualQuantity = it.id
                    ifIf(actualQuantity, localQuantity)
                }, {
                    toast("No internet")
                })
        )
    }

    private fun ifIf(actualQuantity: Int, localQuantity: Int) {
        if (actualQuantity > localQuantity) {
            toast("Found new anime")
            compositeDisposable.add(
                aniApi.getAnimes(localQuantity)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())
                    .subscribe({
//                            picMaker.savePictureToDirectory(it.image, getImageName(it.image))
                        insert(it)
                    }, {
                        toast("Server error")
                    })
            )
        } else {
            toast("You have actual data")
        }
    }

    private fun runOnUiThread(r: Runnable) {
        handler.post(r) // Запуск в главном потоке
    }

//    private fun reLoad(i: Int) {
//        compositeDisposable.add(
//            aniApi.getAnimes(i)
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.computation())
//                .subscribe({
////                    picMaker.savePictureToDirectory(it.image, getImageName(it.image))
//                    try {
//                        insert(it)
//                    } catch (e: Exception) {
//
//                    }
//                }, {
//                    reLoad(i)
//                })
//        )
//    }

    fun insert(list: List<AnimeResponse>) {
        repository.insertLocalEntity(convertResponseListToEntityList(list))
//        Glide.with(getContext()).load(item.image).diskCacheStrategy(DiskCacheStrategy.DATA).preload()
        toast("Anime was uploaded")
        compositeDisposable.dispose()
    }


//    private fun getImageName(title: String): String {
//        return title
//            .replace("https://anilist.pserver.ru/pictures/", "")
//            .replace(".jpg", "")
//            .replace(".png", "")

        // return title
        //    .replace(" ", "")
        //    .replace(":", "").replace(";", "")
        //    .replace("(", "").replace(")", "")
        //    .replace("/", "").replace(".", "")
        //    .replace(",", "").replace("'", "")
        //    .replace("\"", "").replace("?", "")
        //    .replace("!", "").replace("&", "").lowercase()
//    }


    private fun toast(text: String) {
        val snackBar = Snackbar.make(bind.container, text, Snackbar.LENGTH_SHORT)
        snackBar.setAction("SKIP") {
//            Toast.makeText(getContext(), "Ok...", Toast.LENGTH_SHORT).show()
        }
        snackBar.show()
    }
}