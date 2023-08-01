package com.meeweel.anilist.uiAutoTest.mainFragment.models

import androidx.test.espresso.ViewInteraction
import com.meeweel.anilist.EspressoUtils.findViewById
import com.meeweel.anilist.R

class NavBar {
    val main: ViewInteraction get() = findViewById(R.id.main_fragment_nav)
    val watched: ViewInteraction get() = findViewById(R.id.watched_fragment_nav)
    val wanted: ViewInteraction get() = findViewById(R.id.wanted_fragment_nav)
    val notWatched: ViewInteraction get() = findViewById(R.id.not_watched_fragment_nav)
    val unwanted: ViewInteraction get() = findViewById(R.id.unwanted_fragment_nav)
}