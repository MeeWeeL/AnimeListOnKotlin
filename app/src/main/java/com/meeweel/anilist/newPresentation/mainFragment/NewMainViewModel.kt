package com.meeweel.anilist.newPresentation.mainFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meeweel.anilist.domain.AppState
import com.meeweel.anilist.domain.enums.ListState
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.domain.useCases.ChangeAnimeStateUseCase
import com.meeweel.anilist.domain.useCases.GetAnimeListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewMainViewModel(
    private val getAnimeListUseCase: GetAnimeListUseCase = GetAnimeListUseCase(),
    private val changeAnimeStateUseCase: ChangeAnimeStateUseCase = ChangeAnimeStateUseCase()
) : ViewModel() {

    private var currentState = ListState.MAIN
    private var currentList: MutableList<ShortAnime> = mutableListOf()
    private var _listToObserve: MutableLiveData<AppState> = MutableLiveData()
    val listToObserve: LiveData<AppState> get() = _listToObserve

    init {
        loadAnimeList()
    }

    private fun loadAnimeList() {
        viewModelScope.launch(Dispatchers.IO) {
            currentList = getAnimeListUseCase(currentState).toMutableList()
            _listToObserve.postValue(AppState.Success(currentList))
        }
    }

    fun changeAnimeState(animeID: Int, newState: ListState) {
        viewModelScope.launch(Dispatchers.IO) {
            changeAnimeStateUseCase(animeID, newState)
        }
    }

    fun changeListTo(state: ListState) {
        currentState = state
        loadAnimeList()
    }
}