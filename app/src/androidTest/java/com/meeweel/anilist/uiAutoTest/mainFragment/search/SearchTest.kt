package com.meeweel.anilist.uiAutoTest.mainFragment.search

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.meeweel.anilist.EspressoUtils
import com.meeweel.anilist.EspressoUtils.click
import com.meeweel.anilist.EspressoUtils.delay
import com.meeweel.anilist.EspressoUtils.findCardByText
import com.meeweel.anilist.EspressoUtils.isClickable
import com.meeweel.anilist.EspressoUtils.isVisible
import com.meeweel.anilist.presentation.NewMainActivity
import com.meeweel.anilist.uiAutoTest.enums.List
import com.meeweel.anilist.uiAutoTest.mainFragment.models.NavBar
import com.meeweel.anilist.uiAutoTest.mainFragment.models.Search
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class SearchTest {
    private lateinit var scenario: ActivityScenario<NewMainActivity>
    private val navBar = NavBar()
    private val search = Search()

    @Before
    fun setup() {
        EspressoUtils.insertDB()
        scenario = ActivityScenario.launch(NewMainActivity::class.java)
    }

    @Test
    fun checkSearchMain() {
        navBar.main.click()
        checkSearcher(List.MAIN.titleRU, List.UNWANTED.titleRU)
    }

    @Test
    fun checkSearchWatched() {
        navBar.watched.click()
        checkSearcher(List.WATCHED.titleRU, List.UNWANTED.titleRU)
    }

    @Test
    fun checkSearchWanted() {
        navBar.wanted.click()
        checkSearcher(List.WANTED.titleRU, List.UNWANTED.titleRU)
    }

    @Test
    fun checkSearchNotWatched() {
        navBar.notWatched.click()
        checkSearcher(List.NOT_WATCHED.titleRU, List.UNWANTED.titleRU)
    }

    @Test
    fun checkSearchUnwanted() {
        navBar.unwanted.click()
        checkSearcher(List.UNWANTED.titleRU, List.MAIN.titleRU)
    }

    private fun checkSearcher(realText: String, wrongText: String) {
        search.searchField.click()
        delay(DELAY_TIME)
        search.textField.perform(replaceText(realText))
        delay(DELAY_TIME)
        val imm =
            InstrumentationRegistry.getInstrumentation().targetContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        search.textField.check { view, _ ->
            val field = view as View
            imm.hideSoftInputFromWindow(field.windowToken, 0)
        }
        delay(DELAY_TIME)
        findCardByText(realText).isVisible()
        findCardByText(realText).isClickable()
        delay(DELAY_TIME)
        search.textField.perform(replaceText(wrongText))
        delay(DELAY_TIME)
        val isVisible =
            try {
                findCardByText(realText)
                true
            } catch (e: Exception) {
                false
            }
        assert(!isVisible)
        search.closeBtn.click()
        findCardByText(realText).isVisible()
        findCardByText(realText).isClickable()
        search.textField.perform(replaceText(realText))
        delay(DELAY_TIME)
        findCardByText(realText).isVisible()
        findCardByText(realText).isClickable()
    }

    @After
    fun close() {
        scenario.close()
    }

    companion object {
        private const val DELAY_TIME = 300L
    }
}