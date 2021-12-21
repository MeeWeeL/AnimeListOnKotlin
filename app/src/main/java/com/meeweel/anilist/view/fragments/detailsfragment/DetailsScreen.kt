package com.meeweel.anilist.view.fragments.detailsfragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.meeweel.anilist.model.data.Anime

class DetailsScreen(private val aniData: Anime): FragmentScreen {

    override fun createFragment(factory: FragmentFactory): Fragment =
        DetailsFragment.newInstance(aniData)

}