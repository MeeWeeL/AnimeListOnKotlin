package com.meeweel.anilist.uiAutoTest.detailsFragment.tests

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.meeweel.anilist.EspressoUtils
import com.meeweel.anilist.EspressoUtils.click
import com.meeweel.anilist.EspressoUtils.findCardByText
import com.meeweel.anilist.EspressoUtils.isVisible
import com.meeweel.anilist.presentation.NewMainActivity
import com.meeweel.anilist.uiAutoTest.detailsFragment.DetailsScreenModel
import com.meeweel.anilist.uiAutoTest.enums.List
import com.meeweel.anilist.uiAutoTest.mainFragment.models.NavBar
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsTextVisible {
    private val screen = DetailsScreenModel()
    private val navBar = NavBar()
    private lateinit var scenario: ActivityScenario<NewMainActivity>

    @Before
    fun setup() {
        EspressoUtils.insertDB()
        scenario = ActivityScenario.launch(NewMainActivity::class.java)
    }

    @Test
    fun main() {
        navBar.main.click()
        findCardByText(List.MAIN.titleRU).click()
        checkTextVisibility()
    }

    @Test
    fun watched() {
        navBar.watched.click()
        findCardByText(List.WATCHED.titleRU).click()
        checkTextVisibility()
    }

    @Test
    fun notWatched() {
        navBar.notWatched.click()
        findCardByText(List.NOT_WATCHED.titleRU).click()
        checkTextVisibility()
    }

    @Test
    fun wanted() {
        navBar.wanted.click()
        findCardByText(List.WANTED.titleRU).click()
        checkTextVisibility()
    }

    @Test
    fun unwanted() {
        navBar.unwanted.click()
        findCardByText(List.UNWANTED.titleRU).click()
        checkTextVisibility()
    }

    private fun checkTextVisibility() {
        screen.animeImage.isVisible()
        screen.ageRate.isVisible()
        screen.descriptionImage.isVisible()
        screen.seriesQuantity.isVisible()
        screen.releaseDate.isVisible()
        screen.originalTitle.isVisible()
        screen.author.isVisible()
        screen.rating.isVisible()
        screen.genre.isVisible()
        screen.enTitle.isVisible()
        screen.ruTitle.isVisible()
        screen.description.isVisible()
    }

    @After
    fun close() {
        scenario.close()
    }
}