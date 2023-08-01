package com.meeweel.anilist.uiAutoTest.mainFragment.profile

import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.ViewInteraction
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.meeweel.anilist.EspressoUtils
import com.meeweel.anilist.EspressoUtils.click
import com.meeweel.anilist.EspressoUtils.isClickable
import com.meeweel.anilist.EspressoUtils.isVisible
import com.meeweel.anilist.presentation.NewMainActivity
import com.meeweel.anilist.uiAutoTest.mainFragment.models.NavBar
import com.meeweel.anilist.uiAutoTest.mainFragment.models.ToolBar
import com.meeweel.anilist.uiAutoTest.mainFragment.models.dialogs.ProfileDialog
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

// Дописать проверку текста на двух языках
// Дописать проверку изменения каунтеров после перемещения карточки
// Дописать проверку смены темы
@RunWith(AndroidJUnit4::class)
@LargeTest
class ProfileTest {
    private lateinit var scenario: ActivityScenario<NewMainActivity>
    private val toolBar = ToolBar()
    private val profileDialog = ProfileDialog()
    private val navBar = NavBar()

    @Before
    fun setup() {
        EspressoUtils.insertDB()
        scenario = ActivityScenario.launch(NewMainActivity::class.java)
    }

    @Test
    fun dialogVisibilityMain() {
        navBar.main.click()
        toolBar.profileBtn.click()
        fullCheck()
    }

    @Test
    fun countersMain() {
        navBar.main.click()
        toolBar.profileBtn.click()
        checkCounters()
    }

    @Test
    fun dialogVisibilityWatched() {
        navBar.watched.click()
        toolBar.profileBtn.click()
        fullCheck()
    }

    @Test
    fun countersWatched() {
        navBar.main.click()
        toolBar.profileBtn.click()
        checkCounters()
    }

    @Test
    fun dialogVisibilityNotWatched() {
        navBar.notWatched.click()
        toolBar.profileBtn.click()
        fullCheck()
    }

    @Test
    fun countersNotWatched() {
        navBar.main.click()
        toolBar.profileBtn.click()
        checkCounters()
    }

    @Test
    fun dialogVisibilityWanted() {
        navBar.wanted.click()
        toolBar.profileBtn.click()
        fullCheck()
    }

    @Test
    fun countersWanted() {
        navBar.main.click()
        toolBar.profileBtn.click()
        checkCounters()
    }

    @Test
    fun dialogVisibilityUnwanted() {
        navBar.unwanted.click()
        toolBar.profileBtn.click()
        fullCheck()
    }

    @Test
    fun countersUnwanted() {
        navBar.main.click()
        toolBar.profileBtn.click()
        checkCounters()
    }

    private fun checkCounters() {
        profileDialog.inQueueCounter.checkCount("4")
        profileDialog.watchedCounter.checkCount("2")
        profileDialog.notWatchedCounter.checkCount("3")
        profileDialog.unwantedCounter.checkCount("5")
        profileDialog.unsortedCounter.checkCount("86")
    }

    private fun ViewInteraction.checkCount(count: String) {
        check { view, _ ->
            assert((view as TextView).text == count)
        }
    }

    private fun fullCheck() {
        profileDialog.themeCheckBox.isVisible()
        profileDialog.themeCheckBox.isClickable()
        profileDialog.inQueueTitle.isVisible()
        profileDialog.inQueueCounter.isVisible()
        profileDialog.inQueueCopyBtn.isVisible()
        profileDialog.inQueueCopyBtn.isClickable()
        profileDialog.watchedTitle.isVisible()
        profileDialog.watchedCounter.isVisible()
        profileDialog.watchedCopyBtn.isVisible()
        profileDialog.watchedCopyBtn.isClickable()
        profileDialog.notWatchedTitle.isVisible()
        profileDialog.notWatchedCounter.isVisible()
        profileDialog.notWatchedCopyBtn.isVisible()
        profileDialog.notWatchedCopyBtn.isClickable()
        profileDialog.unwantedTitle.isVisible()
        profileDialog.unwantedCounter.isVisible()
        profileDialog.unwantedCopyBtn.isVisible()
        profileDialog.unwantedCopyBtn.isClickable()
        profileDialog.unsortedTitle.isVisible()
        profileDialog.unsortedCounter.isVisible()
        profileDialog.unsortedCopyBtn.isVisible()
        profileDialog.unsortedCopyBtn.isClickable()
    }

    @After
    fun close() {
        scenario.close()
    }
}