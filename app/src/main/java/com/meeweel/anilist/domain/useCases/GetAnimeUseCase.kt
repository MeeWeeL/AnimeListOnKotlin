package com.meeweel.anilist.domain.useCases

import com.meeweel.anilist.domain.models.Anime
import com.meeweel.anilist.domain.repository.Repository
import javax.inject.Inject

class GetAnimeUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(animeId: Int): Anime {
        return repository.getAnimeLocal(animeId)
    }
}