package com.meeweel.anilist.ui.fragments.listFragments.lists.watchedFragment

import com.meeweel.anilist.ui.fragments.listFragments.BaseViewModel

class WatchedViewModel : BaseViewModel() {

    override fun getAnimeList() = repository.getWatchedAnimeListLocal()
}