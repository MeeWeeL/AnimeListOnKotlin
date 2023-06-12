package com.meeweel.anilist.presentation.detailsFragment

import com.meeweel.anilist.domain.models.Anime

sealed class AnimeState {
    data class Success(val animeData: Anime) : AnimeState()
    class Error(val error: Throwable) : AnimeState()
    object Loading : AnimeState()
}