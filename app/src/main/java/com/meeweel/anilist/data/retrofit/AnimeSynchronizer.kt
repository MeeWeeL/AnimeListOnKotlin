package com.meeweel.anilist.data.retrofit

import androidx.lifecycle.MutableLiveData
import com.meeweel.anilist.model.data.AnimeResponse
import com.meeweel.anilist.data.repository.LocalRepository
import com.meeweel.anilist.data.room.toEntityList
import com.meeweel.anilist.data.rx.SchedulerProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class AnimeSynchronizer @Inject constructor(
    private val aniApi: AnimeApi,
    private val repository: LocalRepository,
    private val schedulerProvider: SchedulerProvider
) {

    private var actualQuantity = 0
    private var localQuantity = 0
    private val compositeDisposable = CompositeDisposable()

    private val response: MutableLiveData<Int> = MutableLiveData()
    val syncLiveData get() = response

    fun synchronize() {
        compositeDisposable.add(
            aniApi.getQuantity()  // Получение количества аниме на сервере
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    response.postValue(RESPONSE_CONNECTED)
                    actualQuantity = it.id
                   localQuantity()
                }, {
                    response.postValue(RESPONSE_NO_INTERNET)
                })
        )
    }

    private fun localQuantity() {
        compositeDisposable.add(
            repository.getQuantity() // Получение количества аниме из репо
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    localQuantity = it
                    ifIf()
                }, {
                    response.postValue(RESPONSE_SERVER_ERROR)
                })
        )
    }

    private fun ifIf() {
        if (actualQuantity > localQuantity) {
            compositeDisposable.add(
                aniApi.getAnimes(localQuantity)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())
                    .subscribe({
                        response.postValue(RESPONSE_NEW_ANIME)
                        insert(it)
                    }, {
                        response.postValue(RESPONSE_SERVER_ERROR)
                    })
            )
        } else {
            response.postValue(RESPONSE_ACTUAL_DATA)
        }
    }

    private fun insert(list: List<AnimeResponse>) {
        repository.insertLocalEntity(list.toEntityList())
        response.postValue(list.size)
        compositeDisposable.dispose()
    }

    companion object {
        const val RESPONSE_NO_INTERNET = -1
        const val RESPONSE_CONNECTED = -2
        const val RESPONSE_NEW_ANIME = -3
        const val RESPONSE_SERVER_ERROR = -4
        const val RESPONSE_ACTUAL_DATA = 0
    }
}
