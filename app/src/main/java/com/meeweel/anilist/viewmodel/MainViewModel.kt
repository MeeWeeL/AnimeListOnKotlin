package com.meeweel.anilist.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meeweel.anilist.model.AppState
import com.meeweel.anilist.model.repository.LocalRepository
import com.meeweel.anilist.model.repository.LocalRepositoryImpl
import com.meeweel.anilist.model.room.App.Companion.getEntityDao
import java.lang.Thread.sleep

class MainViewModel(private val repository: LocalRepository = LocalRepositoryImpl(getEntityDao())) :
    ViewModel() {

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
                    repository.let {
                        when (i) {
                            1 -> it.getLocalMainAnimeList()
                            2 -> it.getLocalWatchedAnimeList()
                            3 -> it.getLocalNotWatchedAnimeList()
                            4 -> it.getLocalWantedAnimeList()
                            5 -> it.getLocalUnwantedAnimeList()
                            else -> it.getLocalMainAnimeList()
                        }
                    }
                )
            )
        }.start()
    }
}