package com.meeweel.anilist.ui.fragments.mainfragment

import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.ui.fragments.baselistfragment.BaseViewModel

class MainViewModel : BaseViewModel() {

    override fun getAnimeList(): List<ShortAnime> {
        return repository.getLocalMainAnimeList()
            .sortedBy { if (isRu) it.ruTitle else it.enTitle }
    }
}