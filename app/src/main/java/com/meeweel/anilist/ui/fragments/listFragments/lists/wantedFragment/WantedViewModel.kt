package com.meeweel.anilist.ui.fragments.listFragments.lists.wantedFragment

import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.ui.fragments.listFragments.BaseViewModel

class WantedViewModel : BaseViewModel() {

    override fun getAnimeList(): List<ShortAnime> {
        return repository.getLocalWantedAnimeList()
            .sortedBy { if (isRu) it.ruTitle else it.enTitle }
    }
}