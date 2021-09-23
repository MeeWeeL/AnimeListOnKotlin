package com.meeweel.anilist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meeweel.anilist.model.AppState
import com.meeweel.anilist.model.repository.Repository
import com.meeweel.anilist.model.repository.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(private val repository: Repository = RepositoryImpl()) :
    ViewModel() {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

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
}