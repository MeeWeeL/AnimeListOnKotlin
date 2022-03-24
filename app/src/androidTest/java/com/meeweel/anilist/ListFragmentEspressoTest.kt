package com.meeweel.anilist

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.meeweel.anilist.view.MainActivity
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListFragmentEspressoTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun activitySearch_IsWorking() {
        onView(withId(R.id.input_edit_text)).perform(click())
        onView(withId(R.id.input_edit_text)).perform(replaceText("sister"),
            closeSoftKeyboard())
        onView(withId(R.id.input_edit_text)).perform(pressImeActionButton())
//        if (BuildConfig.TYPE == "FAKE") {
//            onView(isRoot()).perform(delay(5000))
//            onView(withId(R.id.input_edit_text)).check(matches(withText("sister")))
//        } else {
//            onView(isRoot()).perform(delay(2000))
//            onView(withId(R.id.input_edit_text)).check(matches(withText("sister")))
//        }
    }

    @Test
    fun activitySearchBar_IsVisible() {
        onView(withId(R.id.app_bar_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.collapsing_toolbar_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.input_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.input_edit_text)).check(matches(isDisplayed()))
    }

    @Test
    fun activityNavigationBar_IsVisible() {
        onView(withId(R.id.bottom_app_bar)).check(matches(isDisplayed()))
        onView(withId(R.id.nav_bar)).check(matches(isDisplayed()))
    }

    @Test
    fun activityRecyclerView_IsVisible() {
        onView(withId(R.id.mainFragmentRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun activityNavigationBar_IsWorking() {
        onView(withId(R.id.mainFragmentRecyclerView)).check(matches(isDisplayed()))
        onView(isRoot()).perform(delay(6000))
        onView(withId(R.id.not_watched_fragment_nav)).perform(click())
        onView(isRoot()).perform(delay(1000))
        onView(withId(R.id.notWatchedFragmentRecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.input_edit_text)).perform(replaceText("sister"),
            closeSoftKeyboard())
        onView(isRoot()).perform(delay(2000))
        onView(withId(R.id.watched_fragment_nav)).perform(click())
        onView(isRoot()).perform(delay(2000))
        onView(withId(R.id.input_edit_text)).perform(click())
        onView(withId(R.id.input_edit_text)).perform(replaceText("sister"),
            closeSoftKeyboard())
        onView(withId(R.id.watchedFragmentRecyclerView)).check(matches(isDisplayed()))
    }

    private fun delay(time: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $time seconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(time)
            }
        }
    }

    @After
    fun close() {
        scenario.close()
    }
}