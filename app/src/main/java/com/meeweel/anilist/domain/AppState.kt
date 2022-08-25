package com.meeweel.anilist.domain

import com.meeweel.anilist.domain.models.ShortAnime

sealed class AppState {
    data class Success(val animeData: List<ShortAnime>) : AppState()
    class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}