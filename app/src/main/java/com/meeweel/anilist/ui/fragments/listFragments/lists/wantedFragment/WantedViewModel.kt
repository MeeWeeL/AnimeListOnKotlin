package com.meeweel.anilist.ui.fragments.listFragments.lists.wantedFragment

import com.meeweel.anilist.ui.fragments.listFragments.BaseViewModel

class WantedViewModel : BaseViewModel() {

    override fun getAnimeList() = repository.getLocalWantedAnimeList()
}