package com.meeweel.anilist.model

import android.os.Handler
import android.widget.Toast
import com.meeweel.anilist.model.retrofit.AnimeApi
import com.meeweel.anilist.model.data.AnimeResponse
import com.meeweel.anilist.model.repository.LocalRepository
import com.meeweel.anilist.model.data.MaxIdResponse
import com.meeweel.anilist.model.room.convertResponseListToEntityList
import com.meeweel.anilist.model.rx.SchedulerProvider
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class AnimeSynchronizer @Inject constructor(
    private val aniApi: AnimeApi,
    private val repository: LocalRepository,
    private val schedulerProvider: SchedulerProvider
) {

//    private val handler: Handler =
//        Handler(App.ContextHolder.context.mainLooper) // Нужен для запуска главного потока
    private var actualQuantity = 0
    private var localQuantity = repository.getQuantity()
    private val compositeDisposable = CompositeDisposable()

    fun synchronize() {
        compositeDisposable.add(
            aniApi.getQuantity()  // Получение количества аниме на сервере
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
//                .subscribeWith(object : DisposableSingleObserver<MaxIdResponse>() {
//
//                    override fun onSuccess(t: MaxIdResponse) {
//                        actualQuantity = t.id
//                        ifIf(actualQuantity, localQuantity)
//                    }
//
//                    override fun onError(e: Throwable) {
//                        toast("No internet")
//                    }
//                })
                .subscribe({
                    actualQuantity = it.id
                    ifIf(actualQuantity, localQuantity)
                }, {
//                    toast("No internet")
                })
        )
    }

    fun getQuantity() : Single<MaxIdResponse> {
        return aniApi.getQuantity()
    }
    private fun ifIf(actualQuantity: Int, localQuantity: Int) {
        if (actualQuantity > localQuantity) {
//            toast("Found new anime")
            compositeDisposable.add(
                aniApi.getAnimes(localQuantity)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())
                    .subscribe({
                        insert(it)
                    }, {
//                        toast("Server error")
                    })
            )
        } else {
//            toast("You have actual data")
        }
    }

//    private fun runOnUiThread(r: Runnable) {
//        handler.post(r) // Запуск в главном потоке
//    }


    private fun insert(list: List<AnimeResponse>) {
        repository.insertLocalEntity(convertResponseListToEntityList(list))
//        toast("Anime was uploaded")
        compositeDisposable.dispose()
    }

//    private fun toast(text: String) {
//        runOnUiThread {
//            Toast.makeText(App.ContextHolder.context, text, Toast.LENGTH_SHORT).show()
//        }
//    }


//    private fun toast(text: String) {
//        val snackBar = Snackbar.make(bind.container, text, Snackbar.LENGTH_SHORT)
//        snackBar.setAction("SKIP") {
////            Toast.makeText(getContext(), "Ok...", Toast.LENGTH_SHORT).show()
//        }
//        snackBar.show()
//    }
}
