package com.meeweel.anilist.newPresentation.detailsFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meeweel.anilist.domain.AppAnime
import com.meeweel.anilist.domain.models.Anime
import com.meeweel.anilist.domain.useCases.GetAnimeByIdUseCase
import com.meeweel.anilist.model.data.AnimeResponse
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getAnimeByIdUseCase: GetAnimeByIdUseCase = GetAnimeByIdUseCase(),
) : ViewModel() {

    private var currentAnime: AnimeResponse? = null
    private var _listToObserve: MutableLiveData<AppAnime> = MutableLiveData()
    val listToObserve: LiveData<AppAnime> get() = _listToObserve

    fun getAnimeById(animeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            currentAnime = getAnimeByIdUseCase(animeId)
            _listToObserve.postValue(AppAnime.Success(currentAnime!!))
        }
    }

}