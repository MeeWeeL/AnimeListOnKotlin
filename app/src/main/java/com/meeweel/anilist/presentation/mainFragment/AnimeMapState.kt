package com.meeweel.anilist.presentation.mainFragment

import com.meeweel.anilist.domain.enums.ListState
import com.meeweel.anilist.domain.models.ShortAnime

sealed class AnimeMapState {
    data class Success(val animeData: Map<ListState, MutableList<ShortAnime>>) : AnimeMapState()
    object Loading : AnimeMapState()
}