package com.meeweel.anilist.uiAutoTest.mainFragment.models

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withChild
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.meeweel.anilist.EspressoUtils.findViewById
import com.meeweel.anilist.R
import org.hamcrest.Matchers

class Search {
    val searchField: ViewInteraction get() = onView(withId(R.id.search_app_bar))
    val textField: ViewInteraction get() = onView(withId(androidx.appcompat.R.id.search_src_text))
    val closeBtn: ViewInteraction = onView(withId(androidx.appcompat.R.id.search_close_btn))
}