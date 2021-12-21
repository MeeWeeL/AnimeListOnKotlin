package com.meeweel.anilist.view.fragments.notwatched

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen

class NotWatchedScreen: FragmentScreen {

    override fun createFragment(factory: FragmentFactory): Fragment =
        NotWatchedFragment.newInstance()

}