package com.meeweel.anilist.domain.useCases

import com.meeweel.anilist.domain.enums.ListState
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.domain.repository.Repository
import javax.inject.Inject

class GetAnimeListUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(state: ListState): List<ShortAnime> {
        return repository.getAnimeListLocal(state).sortedBy(ShortAnime::ruTitle)
    }
}