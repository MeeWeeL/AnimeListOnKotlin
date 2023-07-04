package com.meeweel.anilist.uiAutoTest.mainFragment.cards

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.meeweel.anilist.EspressoUtils
import com.meeweel.anilist.EspressoUtils.click
import com.meeweel.anilist.EspressoUtils.findCardByText
import com.meeweel.anilist.EspressoUtils.isVisible
import com.meeweel.anilist.R
import com.meeweel.anilist.data.room.isRussian
import com.meeweel.anilist.presentation.NewMainActivity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Проверка корректности работы свайпов
 */
@RunWith(AndroidJUnit4::class)
class CardSwipesWork {

    private lateinit var scenario: ActivityScenario<NewMainActivity>

    @Before
    fun setup() {
        EspressoUtils.insertDB()
        scenario = ActivityScenario.launch(NewMainActivity::class.java)
    }

    @Test
    fun mainSwipeRight() {
        checkSwipe(
            title = if (isRussian) "Бек: Восточная ударная группа" else "Beck: Mongolian Chop Squad",
            isRight = true,
            startPage = R.id.main_fragment_nav,
            endPage = R.id.watched_fragment_nav
        )
    }

    @Test
    fun mainSwipeLeft() {
        checkSwipe(
            title = if (isRussian) "Бек: Восточная ударная группа" else "Beck: Mongolian Chop Squad",
            isRight = false,
            startPage = R.id.main_fragment_nav,
            endPage = R.id.not_watched_fragment_nav
        )
    }

    @Test
    fun watchedSwipeRight() {
        checkSwipe(
            title = if (isRussian) "Волчица и чёрный принц" else "WOLF GIRL AND BLACK PRINCE",
            isRight = true,
            startPage = R.id.watched_fragment_nav,
            endPage = null
        )
    }

    @Test
    fun watchedSwipeLeft() {
        checkSwipe(
            title = if (isRussian) "Волчица и чёрный принц" else "WOLF GIRL AND BLACK PRINCE",
            isRight = false,
            startPage = R.id.watched_fragment_nav,
            endPage = null
        )
    }

    @Test
    fun notWatchedSwipeRight() {
        checkSwipe(
            title = if (isRussian) "Вайолет Эвергарден" else "Violet Evergarden",
            isRight = true,
            startPage = R.id.not_watched_fragment_nav,
            endPage = R.id.wanted_fragment_nav
        )
    }

    @Test
    fun notWatchedSwipeLeft() {
        checkSwipe(
            title = if (isRussian) "Вайолет Эвергарден" else "Violet Evergarden",
            isRight = false,
            startPage = R.id.not_watched_fragment_nav,
            endPage = R.id.unwanted_fragment_nav
        )
    }

    @Test
    fun wantedSwipeRight() {
        checkSwipe(
            title = if (isRussian) "Крутой учитель Онидзука" else "GTO: Great Teacher Onizuka",
            isRight = true,
            startPage = R.id.wanted_fragment_nav,
            endPage = R.id.watched_fragment_nav
        )
    }

    @Test
    fun wantedSwipeLeft() {
        checkSwipe(
            title = if (isRussian) "Крутой учитель Онидзука" else "GTO: Great Teacher Onizuka",
            isRight = false,
            startPage = R.id.wanted_fragment_nav,
            endPage = null
        )
    }

    @Test
    fun unwantedSwipeRight() {
        checkSwipe(
            title = if (isRussian) "Ковбой Бибоп" else "Cowboy Bebop",
            isRight = true,
            startPage = R.id.unwanted_fragment_nav,
            endPage = null
        )
    }

    @Test
    fun unwantedSwipeLeft() {
        checkSwipe(
            title = if (isRussian) "Ковбой Бибоп" else "Cowboy Bebop",
            isRight = false,
            startPage = R.id.unwanted_fragment_nav,
            endPage = null
        )
    }

    private fun checkSwipe(title: String, isRight: Boolean, startPage: Int, endPage: Int?) {
        EspressoUtils.findViewById(startPage).click()
        findCardByText(title).apply { if (isRight) swipeRight() else swipeLeft() }
        endPage?.apply { EspressoUtils.findViewById(this).click() }
        findCardByText(title).isVisible()
    }

    private fun ViewInteraction.swipeRight() {
        perform(ViewActions.swipeRight())
    }

    private fun ViewInteraction.swipeLeft() {
        perform(ViewActions.swipeLeft())
    }

    @After
    fun close() {
        scenario.close()
    }
}