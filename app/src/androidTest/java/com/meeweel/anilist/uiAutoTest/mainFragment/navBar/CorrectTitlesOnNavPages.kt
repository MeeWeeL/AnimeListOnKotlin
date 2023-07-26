package com.meeweel.anilist.uiAutoTest.mainFragment.navBar

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.meeweel.anilist.EspressoUtils.click
import com.meeweel.anilist.EspressoUtils.findCardByText
import com.meeweel.anilist.EspressoUtils.findViewById
import com.meeweel.anilist.EspressoUtils.insertDB
import com.meeweel.anilist.EspressoUtils.isVisible
import com.meeweel.anilist.R
import com.meeweel.anilist.presentation.NewMainActivity
import com.meeweel.anilist.uiAutoTest.enums.List
import com.meeweel.anilist.uiAutoTest.mainFragment.models.NavBar
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.*


/**
 * Правильные ли итемы отображаются на вкладках главного экрана.
 */
@RunWith(AndroidJUnit4::class)
class CorrectTitlesOnNavPages {
    private val navBar = NavBar()
    private lateinit var scenario: ActivityScenario<NewMainActivity>

    @Before
    fun setup() {
        insertDB()
        scenario = ActivityScenario.launch(NewMainActivity::class.java)
    }

    @Test
    fun checkMainNavButton() {
        navBar.wanted.click()
        navBar.main.click()
        findCardByText(List.MAIN.titleRU).isVisible()
    }

    @Test
    fun checkWatchedNavButton() {
        navBar.watched.click()
        findCardByText(List.WATCHED.titleRU).isVisible()
    }

    @Test
    fun checkWantedNavButton() {
        navBar.wanted.click()
        findCardByText(List.WANTED.titleRU).isVisible()
    }

    @Test
    fun checkUnwantedNavButton() {
        navBar.unwanted.click()
        findCardByText(List.UNWANTED.titleRU).isVisible()
    }

    @Test
    fun checkNotWatchedNavButton() {
        navBar.notWatched.click()
        findCardByText(List.NOT_WATCHED.titleRU).isVisible()
    }

    @After
    fun close() {
        scenario.close()
    }
}