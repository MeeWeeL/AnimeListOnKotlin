package com.meeweel.anilist.fragmentstests

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.meeweel.anilist.R
import com.meeweel.anilist.view.fragments.mainfragment.MainFragment
import com.meeweel.anilist.view.fragments.mainfragment.MainFragmentAdapter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainFragmentTest {

    private lateinit var scenario: FragmentScenario<MainFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer()

    }

    @Test
    fun activityNavigationBar_IsVisible() {
        onView(withId(R.id.bottom_app_bar)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.nav_bar)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun activitySearcherInRecycler_IsWork() {
        scenario.onFragment { fragment ->
            fragment.viewModel.findByWord("sister")
        }
        Thread.sleep(2000)
        onView(withId(R.id.mainFragmentRecyclerView))
            .perform(
                RecyclerViewActions.scrollTo<MainFragmentAdapter.MainViewHolder>(
                    hasDescendant(withText("sister"))
                )
            )
    }

    @Test
    fun activityAppBar_IsVisible() {
        onView(withId(R.id.app_bar_layout)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.search_app_bar)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.sort_app_bar)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun activityRecyclerView_IsVisible() {
        onView(withId(R.id.mainFragmentRecyclerView)).check(matches(ViewMatchers.isDisplayed()))
    }
}