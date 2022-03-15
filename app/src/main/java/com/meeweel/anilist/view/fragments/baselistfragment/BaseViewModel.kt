package com.meeweel.anilist.view.fragments.baselistfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meeweel.anilist.R
import com.meeweel.anilist.model.AppState
import com.meeweel.anilist.model.data.ShortAnime
import com.meeweel.anilist.model.repository.LocalRepository
import com.meeweel.anilist.model.repository.LocalRepositoryImpl
import com.meeweel.anilist.model.room.App
import com.meeweel.anilist.viewmodel.Changing

abstract class BaseViewModel : ViewModel() {

    protected val repository: LocalRepository = LocalRepositoryImpl(App.getEntityDao())
    protected val isRu: Boolean = Changing.getContext().resources.getBoolean(R.bool.isRussian)
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

    fun getData(): LiveData<AppState> {
        return liveDataToObserve
    }

    fun getAnimeFromLocalSource() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            liveDataToObserve.postValue(
                AppState.Success(
                    getAnimeList()
                )
            )
        }.start()
    }

    abstract fun getAnimeList(): List<ShortAnime>
}