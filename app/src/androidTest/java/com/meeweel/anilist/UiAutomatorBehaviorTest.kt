package com.meeweel.anilist

import DELAY
import STEPS
import android.content.Context
import android.content.Intent
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class UiAutomatorBehaviorTest {

    private val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val packageName = context.packageName

    @Before
    fun setup() {

        uiDevice.pressHome()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
    }


    @Test
    fun test_Searcher() {

        uiDevice.pressHome()
        uiDevice.swipe(500, 600, 500, 100, STEPS)
        val appViews = UiScrollable(UiSelector().scrollable(false))
        val className = UiSelector().className(TextView::class.java.name)
        val settingsApp = appViews.getChildByText(className, "Settings")
        settingsApp.clickAndWaitForNewWindow()
        val settingsValidation =
            uiDevice.findObject(UiSelector().packageName("com.android.settings"))
        Assert.assertTrue(settingsValidation.exists())
    }

    @Test
    fun test_MainActivityIsStarted() {

        val editText = uiDevice.findObject(By.res(packageName, "input_edit_text"))
        Assert.assertNotNull(editText)
    }

    @Test
    fun test_SearchIsPositive() {

        val editText = uiDevice.findObject(By.res(packageName, "input_edit_text"))
        editText.text = "UiAutomator"
        Espresso.onView(ViewMatchers.withId(R.id.input_edit_text))
            .perform(ViewActions.pressImeActionButton())
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "input_edit_text")),
                TIMEOUT
            )
        Assert.assertEquals(changedText.text.toString(), "UiAutomator")
    }

    @Test
    fun test_MainListDisplayed() {

        Espresso.onView(ViewMatchers.withId(R.id.mainFragmentRecyclerView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Assert.assertTrue(
            uiDevice.findObject(By.res(packageName, "wanted_fragment_nav"))
                .isClickable
        )
        Assert.assertTrue(
            uiDevice.findObject(By.res(packageName, "unwanted_fragment_nav"))
                .isClickable
        )
        Assert.assertTrue(
            uiDevice.findObject(By.res(packageName, "watched_fragment_nav"))
                .isClickable
        )
        Assert.assertTrue(
            uiDevice.findObject(By.res(packageName, "not_watched_fragment_nav"))
                .isClickable
        )
        Assert.assertTrue(
            uiDevice.findObject(By.res(packageName, "main_fragment_nav"))
                .isSelected
        )
    }

    @Test
    fun test_NavigationIsWork() {

        Assert.assertTrue(checkNavMenu(List.MAIN))
        uiDevice.findObject(By.res(packageName, "wanted_fragment_nav")).click()
        val wantedRecycler = uiDevice.wait(
            Until.findObject(By.res(packageName, "wantedFragmentRecyclerView")),
            TIMEOUT
        )
        Assert.assertTrue(wantedRecycler.isEnabled)
        Assert.assertTrue(checkNavMenu(List.WANTED))
        uiDevice.findObject(By.res(packageName, "main_fragment_nav")).click()
        val mainRecycler = uiDevice.wait(
            Until.findObject(By.res(packageName, "mainFragmentRecyclerView")),
            TIMEOUT
        )
        Assert.assertTrue(mainRecycler.isEnabled)
        Assert.assertTrue(checkNavMenu(List.MAIN))
    }

    @Test
    fun test_OpenDetailsScreen() {

        val text = UiSelector().scrollable(true)
        val item = UiScrollable(text).getChildByText(text, ".Koni-chan")
        item.click()
        Assert.assertTrue(uiDevice.findObject(By.res(packageName, "original_title")).isEnabled)
    }

    @Test
    fun test_FindItemOnList() {

        val text = UiSelector().scrollable(true)
        UiScrollable(text).getChildByText(text, ".Koni-chan")
    }

    private fun checkNavMenu(list: List): Boolean {

        var main = true
        var watched = true
        var wanted = true
        var notWatched = true
        var unwanted = true
        when (list) {
            List.MAIN -> main = false
            List.WANTED -> wanted = false
            List.WATCHED -> watched = false
            List.UNWANTED -> unwanted = false
            List.NOT_WATCHED -> notWatched = false
        }
        val selected = when (list) {
            List.MAIN -> assetSelected("main_fragment_nav")
            List.WANTED -> assetSelected("wanted_fragment_nav")
            List.WATCHED -> assetSelected("watched_fragment_nav")
            List.UNWANTED -> assetSelected("unwanted_fragment_nav")
            List.NOT_WATCHED -> assetSelected("not_watched_fragment_nav")
        }
        wanted = uiDevice.findObject(By.res(packageName, "wanted_fragment_nav"))
            .isClickable == wanted
        unwanted = uiDevice.findObject(By.res(packageName, "unwanted_fragment_nav"))
            .isClickable == unwanted
        watched = uiDevice.findObject(By.res(packageName, "watched_fragment_nav"))
            .isClickable == watched
        notWatched = uiDevice.findObject(By.res(packageName, "not_watched_fragment_nav"))
            .isClickable == notWatched
        main = uiDevice.findObject(By.res(packageName, "main_fragment_nav"))
            .isClickable == main

        return wanted && watched && main && unwanted && notWatched && selected
    }

    private fun assetSelected(list: String): Boolean {
        return uiDevice.findObject(By.res(packageName, list))
            .isSelected
    }

    companion object {
        private const val TIMEOUT = DELAY
    }

    enum class List {
        MAIN,
        WATCHED,
        NOT_WATCHED,
        WANTED,
        UNWANTED
    }
}
