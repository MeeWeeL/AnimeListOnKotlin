package com.meeweel.anilist.ui.fragments.detailsfragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.meeweel.anilist.domain.models.ShortAnime

class DetailsScreen(private val aniData: ShortAnime): FragmentScreen {

    override fun createFragment(factory: FragmentFactory): Fragment =
        DetailsFragment.newInstance(aniData)

}