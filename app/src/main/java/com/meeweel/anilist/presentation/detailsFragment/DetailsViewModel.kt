package com.meeweel.anilist.presentation.detailsFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meeweel.anilist.domain.models.Anime
import com.meeweel.anilist.domain.useCases.GetAnimeByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class DetailsViewModel @Inject constructor(private val getAnimeByIdUseCase: GetAnimeByIdUseCase) :
    ViewModel() {

    private lateinit var currentAnime: Anime
    private var _listToObserve: MutableLiveData<AnimeState> = MutableLiveData()
    val listToObserve: LiveData<AnimeState> get() = _listToObserve

    fun getAnimeById(animeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            currentAnime = getAnimeByIdUseCase(animeId)
            _listToObserve.postValue(AnimeState.Success(currentAnime))
        }
    }
}