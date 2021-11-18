package com.meeweel.anilist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meeweel.anilist.R
import com.meeweel.anilist.model.AppState
import com.meeweel.anilist.model.repository.LocalRepository
import com.meeweel.anilist.model.repository.LocalRepositoryImpl
import com.meeweel.anilist.model.room.App.Companion.getEntityDao
import java.lang.Thread.sleep

class MainViewModel(private val repository: LocalRepository = LocalRepositoryImpl(getEntityDao())) :
    ViewModel() {

    private val isRu: Boolean = Changing.getContext().resources.getBoolean(R.bool.isRussian)
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    fun getData(): LiveData<AppState> {
        return liveDataToObserve
    }

    fun getAnimeFromLocalSource() = getDataFromLocalSource(1)
    fun getWatchedAnimeFromLocalSource() = getDataFromLocalSource(2)
    fun getNotWatchedAnimeFromLocalSource() = getDataFromLocalSource(3)
    fun getWantedAnimeFromLocalSource() = getDataFromLocalSource(4)
    fun getUnwantedAnimeFromLocalSource() = getDataFromLocalSource(5)

    private fun getDataFromLocalSource(i: Int) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(100)
            liveDataToObserve.postValue(
                AppState.Success(
                    repository.let { a ->
                        when (i) {
                            1 -> a.getLocalMainAnimeList().sortedBy { if (isRu) it.ruTitle else it.enTitle }
                            2 -> a.getLocalWatchedAnimeList().sortedBy { if (isRu) it.ruTitle else it.enTitle }
                            3 -> a.getLocalNotWatchedAnimeList().sortedBy { if (isRu) it.ruTitle else it.enTitle }
                            4 -> a.getLocalWantedAnimeList().sortedBy { if (isRu) it.ruTitle else it.enTitle }
                            5 -> a.getLocalUnwantedAnimeList().sortedBy { if (isRu) it.ruTitle else it.enTitle }
                            else -> a.getLocalMainAnimeList().sortedBy { if (isRu) it.ruTitle else it.enTitle }
                        }
                    }
                )
            )
        }.start()
    }
}