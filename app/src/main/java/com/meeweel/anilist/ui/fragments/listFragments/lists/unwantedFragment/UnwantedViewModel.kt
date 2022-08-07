package com.meeweel.anilist.ui.fragments.listFragments.lists.unwantedFragment

import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.ui.fragments.listFragments.BaseViewModel

class UnwantedViewModel : BaseViewModel() {

    override fun getAnimeList(): List<ShortAnime> {
        return repository.getLocalUnwantedAnimeList()
            .sortedBy { if (isRu) it.ruTitle else it.enTitle }
    }
}