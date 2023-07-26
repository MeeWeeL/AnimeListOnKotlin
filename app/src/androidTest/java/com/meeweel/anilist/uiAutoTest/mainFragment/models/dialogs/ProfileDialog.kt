package com.meeweel.anilist.uiAutoTest.mainFragment.models.dialogs

import androidx.test.espresso.ViewInteraction
import com.meeweel.anilist.EspressoUtils.findViewById
import com.meeweel.anilist.R

class ProfileDialog {
    val themeCheckBox: ViewInteraction get() = findViewById(R.id.night_mode_checkbox)
    val inQueueTitle: ViewInteraction get() = findViewById(R.id.in_queue_title)
    val inQueueCounter: ViewInteraction get() = findViewById(R.id.wanted_counter)
    val inQueueCopyBtn: ViewInteraction get() = findViewById(R.id.wanted_copy)
    val watchedTitle: ViewInteraction get() = findViewById(R.id.watched_title)
    val watchedCounter: ViewInteraction get() = findViewById(R.id.watched_counter)
    val watchedCopyBtn: ViewInteraction get() = findViewById(R.id.watched_copy)
    val notWatchedTitle: ViewInteraction get() = findViewById(R.id.not_watched_title)
    val notWatchedCounter: ViewInteraction get() = findViewById(R.id.not_watched_counter)
    val notWatchedCopyBtn: ViewInteraction get() = findViewById(R.id.not_watched_copy)
    val unwantedTitle: ViewInteraction get() = findViewById(R.id.unwanted_title)
    val unwantedCounter: ViewInteraction get() = findViewById(R.id.unwanted_counter)
    val unwantedCopyBtn: ViewInteraction get() = findViewById(R.id.unwanted_copy)
    val unsortedTitle: ViewInteraction get() = findViewById(R.id.unsorted_title)
    val unsortedCounter: ViewInteraction get() = findViewById(R.id.main_counter)
    val unsortedCopyBtn: ViewInteraction get() = findViewById(R.id.main_copy)
}