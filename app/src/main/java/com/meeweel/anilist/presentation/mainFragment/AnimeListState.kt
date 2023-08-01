package com.meeweel.anilist.presentation.mainFragment

import com.meeweel.anilist.domain.models.ShortAnime

sealed class AnimeListState {
    data class Success(
        val animeData: List<ShortAnime>,
        val isFiltered: Boolean = false,
    ) : AnimeListState()

    class Error(val error: Throwable) : AnimeListState()
    object Loading : AnimeListState()
}