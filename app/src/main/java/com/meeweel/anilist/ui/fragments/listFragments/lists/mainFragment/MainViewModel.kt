package com.meeweel.anilist.ui.fragments.listFragments.lists.mainFragment

import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.ui.fragments.listFragments.BaseViewModel

class MainViewModel : BaseViewModel() {

    override fun getAnimeList(): List<ShortAnime> {
        return repository.getLocalMainAnimeList()
            .sortedBy { if (isRu) it.ruTitle else it.enTitle }
    }
}