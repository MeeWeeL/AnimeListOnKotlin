package com.meeweel.anilist.ui.fragments.mainfragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen

class MainScreen: FragmentScreen {

    override fun createFragment(factory: FragmentFactory): Fragment =
        MainFragment.newInstance()

}