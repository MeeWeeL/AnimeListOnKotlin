package com.meeweel.anilist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meeweel.anilist.model.AppState
import com.meeweel.anilist.model.data.Anime
import com.meeweel.anilist.model.repository.LocalRepository
import com.meeweel.anilist.model.repository.LocalRepositoryImpl
import com.meeweel.anilist.model.repository.Repository
import com.meeweel.anilist.model.repository.RepositoryImpl
import com.meeweel.anilist.model.room.App.Companion.getNotWatchedDao
import com.meeweel.anilist.model.room.App.Companion.getUnwantedDao
import com.meeweel.anilist.model.room.App.Companion.getWantedDao
import com.meeweel.anilist.model.room.App.Companion.getWatchedDao
import java.lang.Thread.sleep

class MainViewModel(private val repository: Repository = RepositoryImpl()) :
    ViewModel() {
    private var repo: List<Anime> = repository.getAnimeFromLocalStorage()
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    var lr: LocalRepository = LocalRepositoryImpl(getWatchedDao(), getNotWatchedDao(), getWantedDao(), getUnwantedDao(), )
    fun getData(): LiveData<AppState> {
        return liveDataToObserve
    }
    fun getAnimeFromLocalSource() = getDataFromLocalSource()
    fun getWatchedAnimeFromLocalSource() = getWatchedDataFromLocalSource()
    fun getNotWatchedAnimeFromLocalSource() = getNotWatchedDataFromLocalSource()
    fun getWantedAnimeFromLocalSource() = getWantedDataFromLocalSource()
    fun getUnwantedAnimeFromLocalSource() = getUnwantedDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(500)
            liveDataToObserve.postValue(
                AppState.Success(
                    repository.getAnimeFromLocalStorage()
                )
            )
        }.start()
    }
    private fun getWatchedDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(500)
            liveDataToObserve.postValue(
                AppState.Success(
                    repository.getWatchedAnimeFromLocalStorage()
                )
            )
        }.start()
    }
    private fun getNotWatchedDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(500)
            liveDataToObserve.postValue(
                AppState.Success(
                    repository.getNotWatchedAnimeFromLocalStorage()
                )
            )
        }.start()
    }
    private fun getWantedDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(500)
            liveDataToObserve.postValue(
                AppState.Success(
                    repository.getWantedAnimeFromLocalStorage()
                )
            )
        }.start()
    }
    private fun getUnwantedDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(500)
            liveDataToObserve.postValue(
                AppState.Success(
                    repository.getUnwantedAnimeFromLocalStorage()
                )
            )
        }.start()
    }

    fun saving() {

    }
}