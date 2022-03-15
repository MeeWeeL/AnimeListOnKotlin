package com.meeweel.anilist.view.fragments.watchedfragment

import com.meeweel.anilist.model.data.ShortAnime
import com.meeweel.anilist.view.fragments.baselistfragment.BaseViewModel

class WatchedViewModel : BaseViewModel() {

    override fun getAnimeList(): List<ShortAnime> {
        return repository.getLocalWatchedAnimeList()
            .sortedBy { if (isRu) it.ruTitle else it.enTitle }
    }
}