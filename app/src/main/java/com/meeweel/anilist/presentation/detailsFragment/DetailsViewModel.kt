package com.meeweel.anilist.presentation.detailsFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meeweel.anilist.data.repository.RepositoryImpl
import com.meeweel.anilist.data.retrofit.AnimeApi
import com.meeweel.anilist.data.room.EntityDao
import com.meeweel.anilist.domain.models.Anime
import com.meeweel.anilist.domain.repository.Repository
import com.meeweel.anilist.domain.useCases.GetAnimeListUseCase
import com.meeweel.anilist.domain.useCases.GetAnimeUseCase
import com.meeweel.anilist.domain.useCases.RateAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getAnimeUseCase: GetAnimeUseCase,
    private val rateAnimeUseCase: RateAnimeUseCase
) : ViewModel() {
    private lateinit var currentAnime: Anime
    private var _listToObserve: MutableLiveData<AnimeState> = MutableLiveData()
    val listToObserve: LiveData<AnimeState> get() = _listToObserve

    fun getAnimeById(animeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            currentAnime = getAnimeUseCase(animeId)
            _listToObserve.postValue(AnimeState.Success(currentAnime))
        }
    }

    fun rateAnime(animeId: Int, rateScore: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _listToObserve.postValue(AnimeState.Loading)
            try {
                val updatedAnime = rateAnimeUseCase(animeId, rateScore)
                _listToObserve.postValue(AnimeState.Success(updatedAnime))
            } catch (e: Exception) {
                _listToObserve.postValue(AnimeState.Error(e))
            }
        }
    }
}