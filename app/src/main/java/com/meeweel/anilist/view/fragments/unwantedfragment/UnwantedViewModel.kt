package com.meeweel.anilist.view.fragments.unwantedfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meeweel.anilist.R
import com.meeweel.anilist.model.AppState
import com.meeweel.anilist.model.repository.LocalRepository
import com.meeweel.anilist.model.repository.LocalRepositoryImpl
import com.meeweel.anilist.model.room.App
import com.meeweel.anilist.viewmodel.Changing

class UnwantedViewModel(private val repository: LocalRepository = LocalRepositoryImpl(App.getEntityDao())) :
    ViewModel() {

    private val isRu: Boolean = Changing.getContext().resources.getBoolean(R.bool.isRussian)
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    fun getData(): LiveData<AppState> {
        return liveDataToObserve
    }

    fun getUnwantedAnimeFromLocalSource() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            Thread.sleep(100)
            liveDataToObserve.postValue(
                AppState.Success(
                    repository.getLocalUnwantedAnimeList().sortedBy { if (isRu) it.ruTitle else it.enTitle }
                )
            )
        }.start()
    }
}