package com.meeweel.anilist.view.fragments.unwantedfragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen

class UnwantedScreen : FragmentScreen {

    override fun createFragment(factory: FragmentFactory): Fragment =
        UnwantedFragment.newInstance()

}