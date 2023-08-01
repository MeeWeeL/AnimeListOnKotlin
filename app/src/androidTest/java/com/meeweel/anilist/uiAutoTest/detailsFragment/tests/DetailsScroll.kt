package com.meeweel.anilist.uiAutoTest.detailsFragment.tests

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.meeweel.anilist.EspressoUtils
import com.meeweel.anilist.EspressoUtils.click
import com.meeweel.anilist.EspressoUtils.delay
import com.meeweel.anilist.EspressoUtils.findCardByText
import com.meeweel.anilist.EspressoUtils.findViewById
import com.meeweel.anilist.R
import com.meeweel.anilist.presentation.NewMainActivity
import com.meeweel.anilist.uiAutoTest.detailsFragment.DetailsScreenModel
import com.meeweel.anilist.uiAutoTest.enums.List
import com.meeweel.anilist.uiAutoTest.mainFragment.models.NavBar
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsScroll {
    private val screen = DetailsScreenModel()
    private val navBar = NavBar()
    private lateinit var scenario: ActivityScenario<NewMainActivity>

    @Before
    fun setup() {
        EspressoUtils.insertDB()
        scenario = ActivityScenario.launch(NewMainActivity::class.java)
        findCardByText(List.MAIN.titleRU).click()
    }

    @Test
    fun scrollToBottom() {
        screen.description.perform(ViewActions.scrollTo())
        delay(500)
    }

    @After
    fun close() {
        scenario.close()
    }
}