package com.meeweel.anilist.ui.fragments.listFragments.lists.mainFragment

import com.meeweel.anilist.ui.fragments.listFragments.BaseViewModel

class MainViewModel : BaseViewModel() {

    override fun getAnimeList() = repository.getMainAnimeListLocal()
}