package com.meeweel.anilist.uiAutoTest.tests

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.meeweel.anilist.EspressoUtils.findViewById
import com.meeweel.anilist.R
import com.meeweel.anilist.newUI.NewMainActivity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 *  Проверка, правильная ли кнопка навигации нажата (selected) при запуске приложения.
 *  При запуске приложения должена быть нажата кнопка "База" ("Main").
 */
@RunWith(AndroidJUnit4::class)
class Test2001 {

    private lateinit var scenario: ActivityScenario<NewMainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(NewMainActivity::class.java)
    }

    @Test
    fun navBar_isMainSelected() {
        checkSelection(R.id.main_fragment_nav, true)
    }

    @Test
    fun navBar_isWantedNotSelected() {
        checkSelection(R.id.wanted_fragment_nav, false)
    }

    @Test
    fun navBar_isWatchedNotSelected() {
        checkSelection(R.id.watched_fragment_nav, false)
    }

    @Test
    fun navBar_isNotWatchedNotSelected() {
        checkSelection(R.id.not_watched_fragment_nav, false)
    }

    @Test
    fun navBar_isUnwantedNotSelected() {
        checkSelection(R.id.unwanted_fragment_nav, false)
    }

    private fun checkSelection(id: Int, isSelected: Boolean) {
        findViewById(id).check { view, _ ->
            assert((view as BottomNavigationItemView).isSelected == isSelected)
        }
    }

    @After
    fun close() {
        scenario.close()
    }
}