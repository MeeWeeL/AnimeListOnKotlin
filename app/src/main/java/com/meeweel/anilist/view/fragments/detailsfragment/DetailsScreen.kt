package com.meeweel.anilist.view.fragments.detailsfragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.meeweel.anilist.model.data.Anime
import com.meeweel.anilist.model.data.ShortAnime

class DetailsScreen(private val aniData: ShortAnime): FragmentScreen {

    override fun createFragment(factory: FragmentFactory): Fragment =
        DetailsFragment.newInstance(aniData)

}