package com.meeweel.anilist.uiAutoTest.mainFragment.popupMenu

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.action.ViewActions
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.meeweel.anilist.EspressoUtils
import com.meeweel.anilist.EspressoUtils.click
import com.meeweel.anilist.EspressoUtils.isVisible
import com.meeweel.anilist.R
import com.meeweel.anilist.presentation.NewMainActivity
import com.meeweel.anilist.uiAutoTest.enums.List
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PopupWork {

    private lateinit var scenario: ActivityScenario<NewMainActivity>

    @Before
    fun setup() {
        EspressoUtils.insertDB()
        scenario = ActivityScenario.launch(NewMainActivity::class.java)
    }

    @Test
    fun mainToWanted() {
        checkPopupItems(
            R.id.main_fragment_nav,
            List.MAIN.title,
            List.WANTED.text,
            R.id.wanted_fragment_nav
        )
    }

    @Test
    fun mainToUnwanted() {
        checkPopupItems(
            R.id.main_fragment_nav,
            List.MAIN.title,
            List.UNWANTED.text,
            R.id.unwanted_fragment_nav
        )
    }

    @Test
    fun mainToWatched() {
        checkPopupItems(
            R.id.main_fragment_nav,
            List.MAIN.title,
            List.WATCHED.text,
            R.id.watched_fragment_nav
        )
    }

    @Test
    fun mainToNotWatched() {
        checkPopupItems(
            R.id.main_fragment_nav,
            List.MAIN.title,
            List.NOT_WATCHED.text,
            R.id.not_watched_fragment_nav
        )
    }

    @Test
    fun wantedToMain() {
        checkPopupItems(
            R.id.wanted_fragment_nav,
            List.WANTED.title,
            List.MAIN.text,
            R.id.main_fragment_nav
        )
    }

    @Test
    fun wantedToWatched() {
        checkPopupItems(
            R.id.wanted_fragment_nav,
            List.WANTED.title,
            List.WATCHED.text,
            R.id.watched_fragment_nav
        )
    }

    @Test
    fun wantedToNotWatched() {
        checkPopupItems(
            R.id.wanted_fragment_nav,
            List.WANTED.title,
            List.NOT_WATCHED.text,
            R.id.not_watched_fragment_nav
        )
    }

    @Test
    fun wantedToUnwanted() {
        checkPopupItems(
            R.id.wanted_fragment_nav,
            List.WANTED.title,
            List.UNWANTED.text,
            R.id.unwanted_fragment_nav
        )
    }

    @Test
    fun unwantedToMain() {
        checkPopupItems(
            R.id.unwanted_fragment_nav,
            List.UNWANTED.title,
            List.MAIN.text,
            R.id.main_fragment_nav
        )
    }

    @Test
    fun unwantedToWanted() {
        checkPopupItems(
            R.id.unwanted_fragment_nav,
            List.UNWANTED.title,
            List.WANTED.text,
            R.id.wanted_fragment_nav
        )
    }

    @Test
    fun unwantedToWatched() {
        checkPopupItems(
            R.id.unwanted_fragment_nav,
            List.UNWANTED.title,
            List.WATCHED.text,
            R.id.watched_fragment_nav
        )
    }

    @Test
    fun unwantedToNotWatched() {
        checkPopupItems(
            R.id.unwanted_fragment_nav,
            List.UNWANTED.title,
            List.NOT_WATCHED.text,
            R.id.not_watched_fragment_nav
        )
    }

    @Test
    fun watchedToMain() {
        checkPopupItems(
            R.id.watched_fragment_nav,
            List.WATCHED.title,
            List.MAIN.text,
            R.id.main_fragment_nav
        )
    }

    @Test
    fun watchedToNotWatched() {
        checkPopupItems(
            R.id.watched_fragment_nav,
            List.WATCHED.title,
            List.NOT_WATCHED.text,
            R.id.not_watched_fragment_nav
        )
    }

    @Test
    fun watchedToWanted() {
        checkPopupItems(
            R.id.watched_fragment_nav,
            List.WATCHED.title,
            List.WANTED.text,
            R.id.wanted_fragment_nav
        )
    }

    @Test
    fun watchedToUnwanted() {
        checkPopupItems(
            R.id.watched_fragment_nav,
            List.WATCHED.title,
            List.UNWANTED.text,
            R.id.unwanted_fragment_nav
        )
    }

    @Test
    fun notWatchedToMain() {
        checkPopupItems(
            R.id.not_watched_fragment_nav,
            List.NOT_WATCHED.title,
            List.MAIN.text,
            R.id.main_fragment_nav
        )
    }

    @Test
    fun notWatchedToWatched() {
        checkPopupItems(
            R.id.not_watched_fragment_nav,
            List.NOT_WATCHED.title,
            List.WATCHED.text,
            R.id.watched_fragment_nav
        )
    }

    @Test
    fun notWatchedToWanted() {
        checkPopupItems(
            R.id.not_watched_fragment_nav,
            List.NOT_WATCHED.title,
            List.WANTED.text,
            R.id.wanted_fragment_nav
        )
    }

    @Test
    fun notWatchedToUnwanted() {
        checkPopupItems(
            R.id.not_watched_fragment_nav,
            List.NOT_WATCHED.title,
            List.UNWANTED.text,
            R.id.unwanted_fragment_nav
        )
    }

    private fun checkPopupItems(startNavBtnId: Int, cardText: String, menuItem: String, endNavBtnId: Int) {
        EspressoUtils.findViewById(startNavBtnId).click()
        EspressoUtils.findCardByText(cardText).perform(ViewActions.longClick())
        EspressoUtils.delay(150)
        EspressoUtils.findPopupItemByText(menuItem).click()
        EspressoUtils.findViewById(endNavBtnId).click()
        EspressoUtils.findCardByText(cardText).isVisible()
    }

    @After
    fun close() {
        scenario.close()
    }
}