package com.meeweel.anilist.ui.fragments.watchedfragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen

class WatchedScreen : FragmentScreen {

    override fun createFragment(factory: FragmentFactory): Fragment =
        WatchedFragment.newInstance()

}