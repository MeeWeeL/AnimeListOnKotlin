package com.meeweel.anilist.view.fragments.wantedfragment

import com.meeweel.anilist.model.data.ShortAnime
import com.meeweel.anilist.view.fragments.baselistfragment.BaseViewModel

class WantedViewModel : BaseViewModel() {

    override fun getAnimeList(): List<ShortAnime> {
        return repository.getLocalWantedAnimeList()
            .sortedBy { if (isRu) it.ruTitle else it.enTitle }
    }
}