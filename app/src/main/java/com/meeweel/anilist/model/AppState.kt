package com.meeweel.anilist.model

import com.meeweel.anilist.model.data.Anime

sealed class AppState {
    data class Success(val animeData: List<Anime>) : AppState()
    class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}