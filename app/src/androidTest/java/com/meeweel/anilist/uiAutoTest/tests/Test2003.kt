package com.meeweel.anilist.uiAutoTest.tests

import android.view.View
import androidx.core.view.children
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.internal.BaselineLayout
import com.meeweel.anilist.EspressoUtils
import com.meeweel.anilist.R
import com.meeweel.anilist.newUI.NewMainActivity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 *  Проверка видимости текста кнопок навигации.
 *  У всех кнопок навигации должен быть виден текст (label).
 */
@RunWith(AndroidJUnit4::class)
class Test2003 {

    private lateinit var scenario: ActivityScenario<NewMainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(NewMainActivity::class.java)
    }

    @Test
    fun navBar_isMainTextVisible() {
        isLabelVisible(R.id.main_fragment_nav)
    }

    @Test
    fun navBar_isWantedTextVisible() {
        isLabelVisible(R.id.wanted_fragment_nav)
    }

    @Test
    fun navBar_isUnwantedTextVisible() {
        isLabelVisible(R.id.unwanted_fragment_nav)
    }

    @Test
    fun navBar_isWatchedTextVisible() {
        isLabelVisible(R.id.watched_fragment_nav)
    }

    @Test
    fun navBar_isNotWatchedTextVisible() {
        isLabelVisible(R.id.not_watched_fragment_nav)
    }

    private fun isLabelVisible(id: Int) {
        EspressoUtils.findViewById(id).check { view, _ ->
            view as BottomNavigationItemView
            (view.children.elementAt(1) as BaselineLayout).children.apply {
                assert(
                    elementAt(0).visibility == View.VISIBLE ||
                            elementAt(1).visibility == View.VISIBLE
                )
            }
        }
    }

    @After
    fun close() {
        scenario.close()
    }
}