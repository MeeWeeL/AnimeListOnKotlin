package com.meeweel.anilist.uiAutoTest.mainFragment.models

import androidx.appcompat.widget.AppCompatImageButton
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.meeweel.anilist.R
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf

class ToolBar {
    val profileBtn: ViewInteraction
        get() = onView(
            allOf(
                instanceOf(AppCompatImageButton::class.java),
                withParent(withId(R.id.toolbar))
            )
        )
    val filterBtn: ViewInteraction get() = onView(
        allOf(
            instanceOf(AppCompatImageButton::class.java),
            withParent(withId(R.id.filter_app_bar))
        )
    )
}