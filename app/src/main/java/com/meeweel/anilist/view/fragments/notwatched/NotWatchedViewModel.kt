package com.meeweel.anilist.view.fragments.notwatched

import com.meeweel.anilist.model.data.ShortAnime
import com.meeweel.anilist.view.fragments.baselistfragment.BaseViewModel

class NotWatchedViewModel : BaseViewModel() {

    override fun getAnimeList(): List<ShortAnime> {
        return repository.getLocalNotWatchedAnimeList()
            .sortedBy { if (isRu) it.ruTitle else it.enTitle }
    }
}