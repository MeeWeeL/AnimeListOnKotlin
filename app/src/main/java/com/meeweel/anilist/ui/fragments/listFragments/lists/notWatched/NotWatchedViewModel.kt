package com.meeweel.anilist.ui.fragments.listFragments.lists.notWatched

import com.meeweel.anilist.ui.fragments.listFragments.BaseViewModel

class NotWatchedViewModel : BaseViewModel() {

    override fun getAnimeList() = repository.getNotWatchedAnimeListLocal()
}