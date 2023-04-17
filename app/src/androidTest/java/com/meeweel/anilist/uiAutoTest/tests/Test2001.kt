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

@RunWith(AndroidJUnit4::class)
class Test2001 {

    private lateinit var scenario: ActivityScenario<NewMainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(NewMainActivity::class.java)
    }

    @Test
    fun navBar_isMainSelected() {
        findViewById(R.id.main_fragment_nav).check { view, _ ->
            assert((view as BottomNavigationItemView).isSelected)
        }
    }

    @Test
    fun navBar_isWantedNotSelected() {
        findViewById(R.id.wanted_fragment_nav).check { view, _ ->
            assert(!(view as BottomNavigationItemView).isSelected)
        }
    }

    @Test
    fun navBar_isWatchedNotSelected() {
        findViewById(R.id.watched_fragment_nav).check { view, _ ->
            assert(!(view as BottomNavigationItemView).isSelected)
        }
    }

    @Test
    fun navBar_isNotWatchedNotSelected() {
        findViewById(R.id.not_watched_fragment_nav).check { view, _ ->
            assert(!(view as BottomNavigationItemView).isSelected)
        }
    }

    @Test
    fun navBar_isUnwantedNotSelected() {
        findViewById(R.id.unwanted_fragment_nav).check { view, _ ->
            assert(!(view as BottomNavigationItemView).isSelected)
        }
    }

    @After
    fun close() {
        scenario.close()
    }
}