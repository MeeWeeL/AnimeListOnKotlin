package com.meeweel.anilist.ui.fragments.listFragments.lists.notWatched

import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.ui.fragments.listFragments.BaseViewModel

class NotWatchedViewModel : BaseViewModel() {

    override fun getAnimeList(): List<ShortAnime> {
        return repository.getLocalNotWatchedAnimeList()
            .sortedBy { if (isRu) it.ruTitle else it.enTitle }
    }
}