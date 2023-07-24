package com.meeweel.anilist.uiAutoTest.mainFragment.popupMenu

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.action.ViewActions
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.meeweel.anilist.EspressoUtils
import com.meeweel.anilist.EspressoUtils.click
import com.meeweel.anilist.EspressoUtils.delay
import com.meeweel.anilist.EspressoUtils.findCardByText
import com.meeweel.anilist.EspressoUtils.findPopupItemByText
import com.meeweel.anilist.EspressoUtils.findViewById
import com.meeweel.anilist.EspressoUtils.isVisible
import com.meeweel.anilist.R
import com.meeweel.anilist.presentation.NewMainActivity
import com.meeweel.anilist.uiAutoTest.enums.List
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PopupVisibilityCorrect {
    private lateinit var scenario: ActivityScenario<NewMainActivity>
    private val menuItemTextList
        get() = listOf(
            List.MAIN.listNameRU,
            List.WATCHED.listNameRU,
            List.NOT_WATCHED.listNameRU,
            List.WANTED.listNameRU,
            List.UNWANTED.listNameRU,
        )

    @Before
    fun setup() {
        EspressoUtils.insertDB()
        scenario = ActivityScenario.launch(NewMainActivity::class.java)
    }

    @Test
    fun main() {
        checkPopupItems(
            R.id.main_fragment_nav,
            List.MAIN.titleRU,
            List.MAIN.listNameRU
        )
    }

    @Test
    fun wanted() {
        checkPopupItems(
            R.id.wanted_fragment_nav,
            List.WANTED.titleRU,
            List.WANTED.listNameRU
        )
    }

    @Test
    fun unwanted() {
        checkPopupItems(
            R.id.unwanted_fragment_nav,
            List.UNWANTED.titleRU,
            List.UNWANTED.listNameRU
        )
    }

    @Test
    fun watched() {
        checkPopupItems(
            R.id.watched_fragment_nav,
            List.WATCHED.titleRU,
            List.WATCHED.listNameRU
        )
    }

    @Test
    fun notWatched() {
        checkPopupItems(
            R.id.not_watched_fragment_nav,
            List.NOT_WATCHED.titleRU,
            List.NOT_WATCHED.listNameRU
        )
    }

    private fun checkPopupItems(navBtnId: Int, cardText: String, withoutMenuItem: String) {
        findViewById(navBtnId).click()
        findCardByText(cardText).perform(ViewActions.longClick())
        delay(150)
        menuItemTextList.forEach {
            if (it != withoutMenuItem) findPopupItemByText(it).isVisible()
        }
    }

    @After
    fun close() {
        scenario.close()
    }
}