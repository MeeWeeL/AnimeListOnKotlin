package com.meeweel.anilist.presentation.mainFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meeweel.anilist.domain.enums.ListState
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.domain.useCases.ChangeAnimeStateUseCase
import com.meeweel.anilist.domain.useCases.GetAnimeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewMainViewModel @Inject constructor(
    private val getAnimeListUseCase: GetAnimeListUseCase,
    private val changeAnimeStateUseCase: ChangeAnimeStateUseCase,
) : ViewModel() {

    private var currentState = ListState.MAIN
    private var currentList: MutableList<ShortAnime> = mutableListOf()
    private var _listToObserve: MutableLiveData<AnimeListState> = MutableLiveData()
    val listToObserve: LiveData<AnimeListState> get() = _listToObserve
    private var _animeListMapToObserve: MutableLiveData<AnimeMapState> = MutableLiveData()
    val animeListMapToObserve: LiveData<AnimeMapState> get() = _animeListMapToObserve

    init {
        loadAnimeList()
    }

    private fun loadAnimeList() {
        viewModelScope.launch(Dispatchers.IO) {
            currentList = getAnimeListUseCase(currentState).toMutableList()
            _listToObserve.postValue(AnimeListState.Success(currentList))
        }
    }

    fun changeAnimeState(animeID: Int, newState: ListState) {
        viewModelScope.launch(Dispatchers.IO) {
            changeAnimeStateUseCase(animeID, newState)
            loadAnimeList()
        }
    }

    fun getAnimeMapList() {
        viewModelScope.launch(Dispatchers.IO) {
            val animeMap = mutableMapOf<ListState, MutableList<ShortAnime>>()
            for (state in ListState.values()) {
                animeMap[state] = getAnimeListUseCase(state).toMutableList()
            }
            _animeListMapToObserve.postValue(AnimeMapState.Success(animeMap))
        }
    }

    fun changeListTo(state: ListState) {
        currentState = state
        loadAnimeList()
    }
}