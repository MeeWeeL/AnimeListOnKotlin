package com.meeweel.anilist.domain

import com.meeweel.anilist.domain.models.Anime
import com.meeweel.anilist.model.data.AnimeResponse
import io.reactivex.rxjava3.core.Single

sealed class AppAnime {
    data class Success(val animeData: AnimeResponse) : AppAnime()
    class Error(val error: Throwable) : AppAnime()
    object Loading : AppAnime()
}