package com.meeweel.anilist.ui.fragments.listFragments.lists.watchedFragment

import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.ui.fragments.listFragments.BaseViewModel

class WatchedViewModel : BaseViewModel() {

    override fun getAnimeList(): List<ShortAnime> {
        return repository.getLocalWatchedAnimeList()
            .sortedBy { if (isRu) it.ruTitle else it.enTitle }
    }
}