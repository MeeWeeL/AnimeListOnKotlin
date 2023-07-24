package com.meeweel.anilist.uiAutoTest.mainFragment.models

import androidx.test.espresso.ViewInteraction
import com.meeweel.anilist.EspressoUtils.findViewById
import com.meeweel.anilist.R

class NavBar {
    fun main(): ViewInteraction {
        return findViewById(R.id.main_fragment_nav)
    }
    fun watched(): ViewInteraction {
        return findViewById(R.id.watched_fragment_nav)
    }
    fun wanted(): ViewInteraction {
        return findViewById(R.id.wanted_fragment_nav)
    }
    fun notWatched(): ViewInteraction {
        return findViewById(R.id.not_watched_fragment_nav)
    }
    fun unwanted(): ViewInteraction {
        return findViewById(R.id.unwanted_fragment_nav)
    }
}