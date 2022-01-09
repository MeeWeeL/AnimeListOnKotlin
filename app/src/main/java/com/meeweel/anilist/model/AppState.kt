package com.meeweel.anilist.model

import com.meeweel.anilist.model.data.ShortAnime

sealed class AppState {
    data class Success(val animeData: List<ShortAnime>) : AppState()
    class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}