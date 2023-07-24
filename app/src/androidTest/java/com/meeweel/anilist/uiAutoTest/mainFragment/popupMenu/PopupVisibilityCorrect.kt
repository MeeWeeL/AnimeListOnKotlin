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
            List.MAIN.text,
            List.WATCHED.text,
            List.NOT_WATCHED.text,
            List.WANTED.text,
            List.UNWANTED.text,
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
            List.MAIN.title,
            List.MAIN.text
        )
    }

    @Test
    fun wanted() {
        checkPopupItems(
            R.id.wanted_fragment_nav,
            List.WANTED.title,
            List.WANTED.text
        )
    }

    @Test
    fun unwanted() {
        checkPopupItems(
            R.id.unwanted_fragment_nav,
            List.UNWANTED.title,
            List.UNWANTED.text
        )
    }

    @Test
    fun watched() {
        checkPopupItems(
            R.id.watched_fragment_nav,
            List.WATCHED.title,
            List.WATCHED.text
        )
    }

    @Test
    fun notWatched() {
        checkPopupItems(
            R.id.not_watched_fragment_nav,
            List.NOT_WATCHED.title,
            List.NOT_WATCHED.text
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