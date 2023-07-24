package com.meeweel.anilist.uiAutoTest.mainFragment.models.dialogs

import androidx.test.espresso.ViewInteraction
import com.meeweel.anilist.EspressoUtils.findViewById
import com.meeweel.anilist.R

class ProfileDialog {
    fun themeCheckBox(): ViewInteraction {
        return findViewById(R.id.night_mode_checkbox)
    }

    fun inQueueTitle(): ViewInteraction {
        return findViewById(R.id.in_queue_title)
    }

    fun inQueueCounter(): ViewInteraction {
        return findViewById(R.id.wanted_counter)
    }

    fun inQueueCopyBtn(): ViewInteraction {
        return findViewById(R.id.wanted_copy)
    }

    fun watchedTitle(): ViewInteraction {
        return findViewById(R.id.watched_title)
    }

    fun watchedCounter(): ViewInteraction {
        return findViewById(R.id.watched_counter)
    }

    fun watchedCopyBtn(): ViewInteraction {
        return findViewById(R.id.watched_copy)
    }

    fun notWatchedTitle(): ViewInteraction {
        return findViewById(R.id.not_watched_title)
    }

    fun notWatchedCounter(): ViewInteraction {
        return findViewById(R.id.not_watched_counter)
    }

    fun notWatchedCopyBtn(): ViewInteraction {
        return findViewById(R.id.not_watched_copy)
    }

    fun unwantedTitle(): ViewInteraction {
        return findViewById(R.id.unwanted_title)
    }

    fun unwantedCounter(): ViewInteraction {
        return findViewById(R.id.unwanted_counter)
    }

    fun unwantedCopyBtn(): ViewInteraction {
        return findViewById(R.id.unwanted_copy)
    }

    fun unsortedTitle(): ViewInteraction {
        return findViewById(R.id.unsorted_title)
    }

    fun unsortedCounter(): ViewInteraction {
        return findViewById(R.id.main_counter)
    }

    fun unsortedCopyBtn(): ViewInteraction {
        return findViewById(R.id.main_copy)
    }
}