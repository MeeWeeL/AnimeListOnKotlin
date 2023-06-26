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

    private lateinit var scenario: ActivityScenario<NewMainActivity>

    @Before
    fun setup() {
        insertDB()
        scenario = ActivityScenario.launch(NewMainActivity::class.java)
    }

    @Test
    fun checkMainNavButton() {
        findViewById(R.id.wanted_fragment_nav).click()
        findViewById(R.id.main_fragment_nav).click()
        findCardByText("Бек: Восточная ударная группа").isVisible()
    }

    @Test
    fun checkWatchedNavButton() {
        findViewById(R.id.watched_fragment_nav).click()
        findCardByText("Волчица и чёрный принц").isVisible()
    }

    @Test
    fun checkWantedNavButton() {
        findViewById(R.id.wanted_fragment_nav).click()
        findCardByText("Крутой учитель Онидзука").isVisible()
    }

    @Test
    fun checkUnwantedNavButton() {
        findViewById(R.id.unwanted_fragment_nav).click()
        findCardByText("Дневник будущего").isVisible()
    }

    @Test
    fun checkNotWatchedNavButton() {
        findViewById(R.id.not_watched_fragment_nav).click()
        findCardByText("Вайолет Эвергарден").isVisible()
    }

    @After
    fun close() {
        scenario.close()
    }
}