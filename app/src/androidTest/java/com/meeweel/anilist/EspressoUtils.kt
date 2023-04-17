package com.meeweel.anilist

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

object EspressoUtils {

    private const val CLICK_DELAY = 0L


    /** Кнопка назад */
    fun pressBack() {
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
    }

    /** isVisible */
    fun ViewInteraction.isVisible() {
        check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    /** isNotVisible */
    fun ViewInteraction.isNotVisible() {
        check { view, _ -> (view as View).visibility != View.VISIBLE }
    }

    /** isClickable */
    fun ViewInteraction.isClickable() {
        check(ViewAssertions.matches(ViewMatchers.isClickable()))
    }

    /** isNotClickable */
    fun ViewInteraction.isNotClickable() {
        check { view, _ ->
            assert(!(view as View).isClickable)
        }
    }

    /** Нажать */
    fun ViewInteraction.click() {
        perform(ViewActions.click())
        delay(CLICK_DELAY)
    }

    /** Найти вью по id */
    fun findViewById(id: Int): ViewInteraction {
        return Espresso.onView(ViewMatchers.withId(id))
    }

    /** Найти вью по тексту */
    fun findViewByText(text: String): ViewInteraction {
        return Espresso.onView(ViewMatchers.withText(text))
    }

    /** Задержка без остановки главного потока */
    fun delay(millis: Long) {
        Espresso.onView(ViewMatchers.isRoot()).perform(delayAction(millis))
    }

    // ViewAction для задержки без остановки главного потока
    private fun delayAction(time: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = ViewMatchers.isRoot()
            override fun getDescription(): String = "wait for $time seconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(time)
            }
        }
    }
}