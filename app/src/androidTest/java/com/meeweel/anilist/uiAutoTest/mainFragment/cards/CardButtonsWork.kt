package com.meeweel.anilist.uiAutoTest.mainFragment.cards

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.ViewInteraction
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.meeweel.anilist.EspressoUtils
import com.meeweel.anilist.EspressoUtils.click
import com.meeweel.anilist.EspressoUtils.isVisible
import com.meeweel.anilist.R
import com.meeweel.anilist.data.room.isRussian
import com.meeweel.anilist.presentation.NewMainActivity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Правильные ли отрабатывают кнопки на карточках.
 */
@RunWith(AndroidJUnit4::class)
class CardButtonsWork {

    private lateinit var scenario: ActivityScenario<NewMainActivity>

    @Before
    fun setup() {
        EspressoUtils.insertDB()
        scenario = ActivityScenario.launch(NewMainActivity::class.java)
    }

    @Test
    fun checkButton_WatchedFromMain_Visible() {
        checkButtonVisibility(
            R.id.main_fragment_nav,
            if (isRussian) "Бек: Восточная ударная группа" else "Beck: Mongolian Chop Squad",
            R.id.watched_btn
        )
    }

    @Test
    fun checkButton_WatchedFromMain_WorkCorrect() {
        checkButtonWork(
            R.id.main_fragment_nav,
            if (isRussian) "Бек: Восточная ударная группа" else "Beck: Mongolian Chop Squad",
            R.id.watched_btn,
            R.id.watched_fragment_nav
        )
    }

    @Test
    fun checkButton_WatchedFromWanted_Visible() {
        checkButtonVisibility(
            R.id.wanted_fragment_nav,
            if (isRussian) "Крутой учитель Онидзука" else "GTO: Great Teacher Onizuka",
            R.id.watched_btn_on_wanted
        )
    }

    @Test
    fun checkButton_WatchedFromWanted_WorkCorrect() {
        checkButtonWork(
            R.id.wanted_fragment_nav,
            if (isRussian) "Крутой учитель Онидзука" else "GTO: Great Teacher Onizuka",
            R.id.watched_btn_on_wanted,
            R.id.watched_fragment_nav
        )
    }

    @Test
    fun checkButton_NotWatched_Visible() {
        checkButtonVisibility(
            R.id.main_fragment_nav,
            if (isRussian) "Бек: Восточная ударная группа" else "Beck: Mongolian Chop Squad",
            R.id.not_watched_btn
        )
    }

    @Test
    fun checkButton_NotWatched_WorkCorrect() {
        checkButtonWork(
            R.id.main_fragment_nav,
            if (isRussian) "Бек: Восточная ударная группа" else "Beck: Mongolian Chop Squad",
            R.id.not_watched_btn,
            R.id.not_watched_fragment_nav
        )
    }

    @Test
    fun checkButton_Wanted_Visible() {
        checkButtonVisibility(
            R.id.not_watched_fragment_nav,
            if (isRussian) "Вайолет Эвергарден" else "Violet Evergarden",
            R.id.wanted_btn
        )
    }

    @Test
    fun checkButton_Wanted_WorkCorrect() {
        checkButtonWork(
            R.id.not_watched_fragment_nav,
            if (isRussian) "Вайолет Эвергарден" else "Violet Evergarden",
            R.id.wanted_btn,
            R.id.wanted_fragment_nav
        )
    }

    @Test
    fun checkButton_Unwanted_Visible() {
        checkButtonVisibility(
            R.id.not_watched_fragment_nav,
            if (isRussian) "Вайолет Эвергарден" else "Violet Evergarden",
            R.id.unwanted_btn
        )
    }

    @Test
    fun checkButton_Unwanted_WorkCorrect() {
        checkButtonWork(
            R.id.not_watched_fragment_nav,
            if (isRussian) "Вайолет Эвергарден" else "Violet Evergarden",
            R.id.unwanted_btn,
            R.id.unwanted_fragment_nav
        )
    }

    private fun checkButtonVisibility(
        startNavId: Int,
        cardTitle: String,
        buttonId: Int
    ) {
        getBtn(startNavId, cardTitle, buttonId).isVisible()
    }

    private fun checkButtonWork(
        startNavId: Int,
        cardTitle: String,
        buttonId: Int,
        endNavId: Int
    ) {
        getBtn(startNavId, cardTitle, buttonId).click()
        EspressoUtils.findViewById(endNavId).click()
        EspressoUtils.findCardByText(cardTitle).isVisible()
    }

    private fun getBtn(
        startNavId: Int,
        cardTitle: String,
        buttonId: Int
    ): ViewInteraction {
        EspressoUtils.findViewById(startNavId).click()
        return EspressoUtils.findCardButtonByAnimeTitleAndId(
            cardTitle,
            buttonId
        )
    }

    @After
    fun close() {
        scenario.close()
    }
}