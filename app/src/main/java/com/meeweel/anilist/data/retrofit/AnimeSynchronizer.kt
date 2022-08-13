package com.meeweel.anilist.data.retrofit

import androidx.lifecycle.MutableLiveData
import com.meeweel.anilist.data.repository.LocalRepository
import com.meeweel.anilist.data.room.convertResponseListToEntityList
import com.meeweel.anilist.data.rx.SchedulerProvider
import com.meeweel.anilist.model.data.AnimeResponse
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class AnimeSynchronizer @Inject constructor(
    private val aniApi: AnimeApi,
    private val repository: LocalRepository,
    private val schedulerProvider: SchedulerProvider
) {

    private var actualQuantity = 0
    private var localQuantity = repository.getQuantity()
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
                    ifIf(actualQuantity, localQuantity)
                }, {
                    response.postValue(RESPONSE_NO_INTERNET)
                })
        )
    }

    private fun ifIf(actualQuantity: Int, localQuantity: Int) {
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
        repository.insertLocalEntity(convertResponseListToEntityList(list))
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
