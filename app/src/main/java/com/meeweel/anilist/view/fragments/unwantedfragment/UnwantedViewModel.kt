package com.meeweel.anilist.view.fragments.unwantedfragment

import com.meeweel.anilist.model.data.ShortAnime
import com.meeweel.anilist.view.fragments.baselistfragment.BaseViewModel

class UnwantedViewModel : BaseViewModel() {

    override fun getAnimeList(): List<ShortAnime> {
        return repository.getLocalUnwantedAnimeList()
            .sortedBy { if (isRu) it.ruTitle else it.enTitle }
    }
}