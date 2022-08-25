package com.meeweel.anilist.ui.fragments.listFragments.lists.unwantedFragment

import com.meeweel.anilist.ui.fragments.listFragments.BaseViewModel

class UnwantedViewModel : BaseViewModel() {

    override fun getAnimeList() = repository.getLocalUnwantedAnimeList()
}