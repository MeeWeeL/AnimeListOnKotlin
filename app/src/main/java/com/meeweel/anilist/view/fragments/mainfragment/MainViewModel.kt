package com.meeweel.anilist.view.fragments.mainfragment

import com.meeweel.anilist.model.data.ShortAnime
import com.meeweel.anilist.view.fragments.baselistfragment.BaseViewModel

class MainViewModel : BaseViewModel() {

    override fun getAnimeList(): List<ShortAnime> {
        return repository.getLocalMainAnimeList()
            .sortedBy { if (isRu) it.ruTitle else it.enTitle }
    }
}