package com.meeweel.anilist.view.fragments.wantedfragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen

class WantedScreen : FragmentScreen {

    override fun createFragment(factory: FragmentFactory): Fragment =
        WantedFragment.newInstance()

}