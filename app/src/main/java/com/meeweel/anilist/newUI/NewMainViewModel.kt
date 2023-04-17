package com.meeweel.anilist.newUI

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.meeweel.anilist.app.App.ContextHolder.context
import com.meeweel.anilist.data.repository.Repository
import com.meeweel.anilist.data.repository.RepositoryImpl
import com.meeweel.anilist.data.retrofit.RetrofitImpl
import com.meeweel.anilist.data.room.EntityDataBase
import com.meeweel.anilist.di.RepositoryModule
import com.meeweel.anilist.domain.AppState
import com.meeweel.anilist.domain.models.ShortAnime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewMainViewModel(private val repository: Repository = RepositoryImpl(
    RetrofitImpl().getService(),
    Room.databaseBuilder(context, EntityDataBase::class.java, RepositoryModule.DB_NAME).allowMainThreadQueries()
        .build().entityDao()
)) : ViewModel() {



    private var currentState = ListState.MAIN

    private var currentList: MutableList<ShortAnime> = mutableListOf()

    private var currentListData: MutableLiveData<AppState> = MutableLiveData()
    fun getCurrentListData(): LiveData<AppState> = currentListData

    init {
        loadAnimeList(currentState)
    }

    private fun loadAnimeList(listState: ListState) {
        viewModelScope.launch(Dispatchers.IO) {
            currentList = repository.getAnimeListLocal(listState).sortedBy(ShortAnime::ruTitle).toMutableList()
            currentListData.postValue(AppState.Success(currentList))
        }
    }

    fun changeListTo(state: ListState) {
        currentState = state
        loadAnimeList(state)
    }
}