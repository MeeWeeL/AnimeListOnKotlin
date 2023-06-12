package com.meeweel.anilist.domain.useCases

import com.meeweel.anilist.domain.enums.ListState
import com.meeweel.anilist.domain.repository.Repository
import javax.inject.Inject

class ChangeAnimeStateUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(animeId: Int, newState: ListState) {
        repository.updateEntityLocal(animeId, newState)
    }
}