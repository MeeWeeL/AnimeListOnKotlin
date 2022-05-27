package com.meeweel.anilist.ui.fragments.wantedfragment

import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.ui.fragments.baselistfragment.BaseViewModel

class WantedViewModel : BaseViewModel() {

    override fun getAnimeList(): List<ShortAnime> {
        return repository.getLocalWantedAnimeList()
            .sortedBy { if (isRu) it.ruTitle else it.enTitle }
    }
}