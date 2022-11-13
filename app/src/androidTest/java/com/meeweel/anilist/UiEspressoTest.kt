package com.meeweel.anilist

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.meeweel.anilist.ui.MainActivity
import com.meeweel.anilist.ui.fragments.listFragments.lists.mainFragment.MainFragmentAdapter
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UiEspressoTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun activityAppBar_IsVisible() {
        onView(withId(R.id.app_bar_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.collapsing_toolbar_layout)).check(matches(isDisplayed()))
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
    fun activityNavigationToNotWatched_IsWorking() {
        toNotWatched()
        onView(withId(R.id.notWatchedFragmentRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun activityNavigationToWanted_IsWorking() {
        toWanted()
        onView(withId(R.id.wantedFragmentRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun activityNavigationToWatched_IsWorking() {
        toWatched()
        onView(withId(R.id.watchedFragmentRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun activityNavigationToUnwanted_IsWorking() {
        toUnwanted()
        onView(withId(R.id.unwantedFragmentRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun activityNavigation_IsWorking() {
        onView(withId(R.id.mainFragmentRecyclerView)).check(matches(isDisplayed()))
        toNotWatched()
        onView(withId(R.id.notWatchedFragmentRecyclerView)).check(matches(isDisplayed()))
        toMain()
        onView(withId(R.id.mainFragmentRecyclerView)).check(matches(isDisplayed()))
        toWatched()
        onView(withId(R.id.watchedFragmentRecyclerView)).check(matches(isDisplayed()))
        toMain()
        onView(withId(R.id.mainFragmentRecyclerView)).check(matches(isDisplayed()))
        toWanted()
        onView(withId(R.id.wantedFragmentRecyclerView)).check(matches(isDisplayed()))
        toMain()
        onView(withId(R.id.mainFragmentRecyclerView)).check(matches(isDisplayed()))
        toUnwanted()
        onView(withId(R.id.unwantedFragmentRecyclerView)).check(matches(isDisplayed()))
        toMain()
        onView(withId(R.id.mainFragmentRecyclerView)).check(matches(isDisplayed()))
    }

    private fun toMain() {
        onView(withId(R.id.main_fragment_nav)).perform(click())
        onView(isRoot()).perform(delay(navigationDelay))
    }

    private fun toWatched() {
        onView(withId(R.id.watched_fragment_nav)).perform(click())
        onView(isRoot()).perform(delay(navigationDelay))
    }

    private fun toWanted() {
        onView(withId(R.id.wanted_fragment_nav)).perform(click())
        onView(isRoot()).perform(delay(navigationDelay))
    }

    private fun toNotWatched() {
        onView(withId(R.id.not_watched_fragment_nav)).perform(click())
        onView(isRoot()).perform(delay(navigationDelay))
    }

    private fun toUnwanted() {
        onView(withId(R.id.unwanted_fragment_nav)).perform(click())
        onView(isRoot()).perform(delay(navigationDelay))
    }

    @Test
    fun fragmentMainProfileElements_isVisible() {
        profileElementsIsVisible()
    }

    @Test
    fun fragmentNotWatchedProfileElements_isVisible() {
        onView(withId(R.id.not_watched_fragment_nav)).perform(click())
        profileElementsIsVisible()
    }

    @Test
    fun fragmentWatchedProfileElements_isVisible() {
        onView(withId(R.id.watched_fragment_nav)).perform(click())
        profileElementsIsVisible()
    }

    @Test
    fun fragmentWantedProfileElements_isVisible() {
        onView(withId(R.id.wanted_fragment_nav)).perform(click())
        profileElementsIsVisible()
    }

    @Test
    fun fragmentUnwantedProfileElements_isVisible() {
        onView(withId(R.id.unwanted_fragment_nav)).perform(click())
        profileElementsIsVisible()
    }

    private fun profileElementsIsVisible() {
        onView(isRoot()).perform(delay(navigationDelay))
        onView(
            allOf(
                instanceOf(ImageButton::class.java),
                withParent(withId(R.id.toolbar))
            )
        ).perform(click())
        onView(isRoot()).perform(delay(navigationDelay))
        onView(withId(R.id.night_mode_checkbox)).check(matches(isDisplayed()))
        onView(withId(R.id.wanted_copy)).check(matches(isDisplayed()))
        onView(withId(R.id.watched_copy)).check(matches(isDisplayed()))
        onView(withId(R.id.not_watched_copy)).check(matches(isDisplayed()))
        onView(withId(R.id.unwanted_copy)).check(matches(isDisplayed()))
        onView(withId(R.id.main_copy)).check(matches(isDisplayed()))
        onView(withId(R.id.wanted_counter)).check(matches(isDisplayed()))
        onView(withId(R.id.watched_counter)).check(matches(isDisplayed()))
        onView(withId(R.id.not_watched_counter)).check(matches(isDisplayed()))
        onView(withId(R.id.unwanted_counter)).check(matches(isDisplayed()))
        onView(withId(R.id.main_counter)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentMainProfileCounters_isNormal() {
        profileCounters_isNormal()
    }

    @Test
    fun fragmentNotWatchedProfileCounters_isNormal() {
        onView(withId(R.id.not_watched_fragment_nav)).perform(click())
        profileCounters_isNormal()
    }

    @Test
    fun fragmentWatchedProfileCounters_isNormal() {
        onView(withId(R.id.watched_fragment_nav)).perform(click())
        profileCounters_isNormal()
    }

    @Test
    fun fragmentWantedProfileCounters_isNormal() {
        onView(withId(R.id.wanted_fragment_nav)).perform(click())
        profileCounters_isNormal()
    }

    @Test
    fun fragmentUnwantedProfileCounters_isNormal() {
        onView(withId(R.id.unwanted_fragment_nav)).perform(click())
        profileCounters_isNormal()
    }

    private fun profileCounters_isNormal() {
        onView(isRoot()).perform(delay(navigationDelay))
        onView(
            allOf(
                instanceOf(ImageButton::class.java),
                withParent(withId(R.id.toolbar))
            )
        ).perform(click())
        onView(isRoot()).perform(delay(navigationDelay))
        onView(withId(R.id.wanted_copy)).check(matches(isClickable()))
        onView(withId(R.id.watched_copy)).check(matches(isClickable()))
        onView(withId(R.id.not_watched_copy)).check(matches(isClickable()))
        onView(withId(R.id.unwanted_copy)).check(matches(isClickable()))
        onView(withId(R.id.main_copy)).check(matches(isClickable()))

        onView(withId(R.id.wanted_counter)).check { view, _ -> view is TextView }
        onView(withId(R.id.watched_counter)).check { view, _ -> view is TextView }
        onView(withId(R.id.not_watched_counter)).check { view, _ -> view is TextView }
        onView(withId(R.id.unwanted_counter)).check { view, _ -> view is TextView }
        onView(withId(R.id.main_counter)).check { view, _ -> view is TextView }

        var mainCounter = ""
        var watchedCounter = ""
        var notWatchedCounter = ""
        var wantedCounter = ""
        var unwantedCounter = ""

        onView(withId(R.id.wanted_counter)).check { view, _ ->
            watchedCounter = (view as TextView).text.toString()
            watchedCounter.isNotEmpty()
        }
        onView(withId(R.id.watched_counter)).check { view, _ ->
            wantedCounter = (view as TextView).text.toString()
            wantedCounter.isNotEmpty()
        }
        onView(withId(R.id.not_watched_counter)).check { view, _ ->
            notWatchedCounter = (view as TextView).text.toString()
            notWatchedCounter.isNotEmpty()
        }
        onView(withId(R.id.unwanted_counter)).check { view, _ ->
            unwantedCounter = (view as TextView).text.toString()
            unwantedCounter.isNotEmpty()
        }
        onView(withId(R.id.main_counter)).check { view, _ ->
            mainCounter = (view as TextView).text.toString()
            mainCounter.isNotEmpty()
        }
        // Хотя бы где-то должны быть итемы
        assert(mainCounter != "0" || unwantedCounter != "0" || notWatchedCounter != "0" || watchedCounter != "0" || wantedCounter != "0")
    }

    @Test
    fun fragmentMainFilter_isWork() {
        onView(isRoot()).perform(delay(navigationDelay))
        onView(withId(R.id.filter_app_bar)).perform(click())
        onView(isRoot()).perform(delay(navigationDelay))

        onView(withId(R.id.genre_spinner)).check(matches(isDisplayed()))
        onView(withId(R.id.years_range_slider)).check(matches(isDisplayed()))
        onView(withId(R.id.sort_spinner)).check(matches(isDisplayed()))
        onView(withId(R.id.clear_button)).check(matches(isDisplayed()))
        onView(withId(R.id.ok_button)).check(matches(isDisplayed()))

        onView(withId(R.id.ok_button)).perform(click())
        onView(isRoot()).perform(delay(navigationDelay))

        onView(withId(R.id.genre_spinner)).check(doesNotExist())
        onView(withId(R.id.years_range_slider)).check(doesNotExist())
        onView(withId(R.id.sort_spinner)).check(doesNotExist())
        onView(withId(R.id.clear_button)).check(doesNotExist())
        onView(withId(R.id.ok_button)).check(doesNotExist())
    }

    @Test
    fun fragmentRecyclerItemDescription_detailsBar_isVisible() {
        openDescriptionOfRecyclerItem()
        onView(withId(R.id.details_bar)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentRecyclerItemDescriptionZoom_detailsBar_isVisible() {
        openDescriptionOfRecyclerItemWithZoom()
        onView(withId(R.id.details_bar)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentRecyclerItemDescription_detailsBarImage_isVisible() {
        openDescriptionOfRecyclerItem()
        onView(withId(R.id.details_bar_image)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentRecyclerItemDescriptionZoom_detailsBarImage_isVisible() {
        openDescriptionOfRecyclerItemWithZoom()
        onView(withId(R.id.details_bar_image)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentRecyclerItemDescription_detailsToolbar_isVisible() {
        openDescriptionOfRecyclerItem()
        onView(withId(R.id.details_toolbar)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentRecyclerItemDescriptionZoom_detailsToolbar_isVisible() {
        openDescriptionOfRecyclerItemWithZoom()
        onView(withId(R.id.details_toolbar)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentRecyclerItemDescription_detailsScrollView_isVisible() {
        openDescriptionOfRecyclerItem()
        onView(withId(R.id.details_scroll_view)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentRecyclerItemDescriptionZoom_detailsScrollView_isVisible() {
        openDescriptionOfRecyclerItemWithZoom()
        onView(withId(R.id.details_scroll_view)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentRecyclerItemDescription_originalTitle_isVisible() {
        openDescriptionOfRecyclerItem()
        onView(withId(R.id.original_title)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentRecyclerItemDescriptionZoom_originalTitle_isVisible() {
        openDescriptionOfRecyclerItemWithZoom()
        onView(withId(R.id.original_title)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentRecyclerItemDescription_animeImage_isVisible() {
        openDescriptionOfRecyclerItem()
        onView(withId(R.id.anime_image)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentRecyclerItemDescriptionZoom_animeImage_isVisible() {
        openDescriptionOfRecyclerItemWithZoom()
        onView(withId(R.id.anime_image)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentRecyclerItemDescription_releaseData_isVisible() {
        openDescriptionOfRecyclerItem()
        onView(withId(R.id.release_Data)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentRecyclerItemDescriptionZoom_releaseData_isVisible() {
        openDescriptionOfRecyclerItemWithZoom()
        onView(withId(R.id.release_Data)).check(matches(not(isDisplayed())))
    }

    @Test
    fun fragmentRecyclerItemDescription_releaseAuthor_isVisible() {
        openDescriptionOfRecyclerItem()
        onView(withId(R.id.release_Author)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentRecyclerItemDescriptionZoom_releaseAuthor_isVisible() {
        openDescriptionOfRecyclerItemWithZoom()
        onView(withId(R.id.release_Author)).check(matches(not(isDisplayed())))
    }

    @Test
    fun fragmentRecyclerItemDescription_releaseRating_isVisible() {
        openDescriptionOfRecyclerItem()
        onView(withId(R.id.release_rating)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentRecyclerItemDescriptionZoom_releaseRating_isVisible() {
        openDescriptionOfRecyclerItemWithZoom()
        onView(withId(R.id.release_rating)).check(matches(not(isDisplayed())))
    }

    @Test
    fun fragmentRecyclerItemDescription_releaseAgeRate_isVisible() {
        openDescriptionOfRecyclerItem()
        onView(withId(R.id.release_age_rate)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentRecyclerItemDescriptionZoom_releaseAgeRate_isVisible() {
        openDescriptionOfRecyclerItemWithZoom()
        onView(withId(R.id.release_age_rate)).check(matches(not(isDisplayed())))
    }

    @Test
    fun fragmentRecyclerItemDescription_seriesQuantity_isVisible() {
        openDescriptionOfRecyclerItem()
        onView(withId(R.id.series_quantity)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentRecyclerItemDescriptionZoom_seriesQuantity_isVisible() {
        openDescriptionOfRecyclerItemWithZoom()
        onView(withId(R.id.series_quantity)).check(matches(not(isDisplayed())))
    }

    @Test
    fun fragmentRecyclerItemDescription_releaseGenre_isVisible() {
        openDescriptionOfRecyclerItem()
        onView(withId(R.id.release_Genre)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentRecyclerItemDescriptionZoom_releaseGenre_isVisible() {
        openDescriptionOfRecyclerItemWithZoom()
        onView(withId(R.id.release_Genre)).check(matches(not(isDisplayed())))
    }

    @Test
    fun fragmentRecyclerItemDescription_detailsDescriptionImage_isVisible() {
        openDescriptionOfRecyclerItem()
        onView(withId(R.id.details_description_image)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentRecyclerItemDescriptionZoom_detailsDescriptionImage_isVisible() {
        openDescriptionOfRecyclerItemWithZoom()
        onView(withId(R.id.details_description_image)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentRecyclerItemDescription_englishTitle_isVisible() {
        openDescriptionOfRecyclerItem()
        onView(withId(R.id.english_title)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentRecyclerItemDescriptionZoom_englishTitle_isVisible() {
        openDescriptionOfRecyclerItemWithZoom()
        onView(withId(R.id.english_title)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentRecyclerItemDescription_descriptionLabel_isVisible() {
        openDescriptionOfRecyclerItem()
        onView(withId(R.id.descriptionLabel)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentRecyclerItemDescriptionZoom_descriptionLabel_isVisible() {
        openDescriptionOfRecyclerItemWithZoom()
        onView(withId(R.id.descriptionLabel)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentRecyclerItemDescription_descriptionValue_isVisible() {
        openDescriptionOfRecyclerItem()
        onView(withId(R.id.description_value)).check(matches(isDisplayed()))
    }

    @Test
    fun fragmentRecyclerItemDescriptionZoom_descriptionValue_isVisible() {
        openDescriptionOfRecyclerItemWithZoom()
        onView(withId(R.id.description_value)).check(matches(isDisplayed()))
    }

    private fun openDescriptionOfRecyclerItem() {
        onView(isRoot()).perform(delay(navigationDelay))
        onView(withId(R.id.mainFragmentRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<MainFragmentAdapter.MainViewHolder>(
                0,
                click()
            )
        )
        onView(isRoot()).perform(delay(navigationDelay))
    }

    private fun openDescriptionOfRecyclerItemWithZoom() {
        openDescriptionOfRecyclerItem()
        onView(withId(R.id.anime_image)).perform(click())
        onView(isRoot()).perform(delay(navigationDelay))
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

    companion object {
        const val navigationDelay = 150L
    }
}