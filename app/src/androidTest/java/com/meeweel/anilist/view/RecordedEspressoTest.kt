package com.meeweel.anilist.view


import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import com.meeweel.anilist.R
import com.meeweel.anilist.ui.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class RecordedEspressoTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun recordedEspressoTest() {
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.not_watched_fragment_nav), withContentDescription("Not watched"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_bar),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val bottomNavigationItemView2 = onView(
            allOf(
                withId(R.id.wanted_fragment_nav), withContentDescription("Wanted"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_bar),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView2.perform(click())

        val bottomNavigationItemView3 = onView(
            allOf(
                withId(R.id.unwanted_fragment_nav), withContentDescription("Unwanted"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_bar),
                        0
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView3.perform(click())

        val bottomNavigationItemView4 = onView(
            allOf(
                withId(R.id.watched_fragment_nav), withContentDescription("Watched"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_bar),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView4.perform(click())

        val bottomNavigationItemView5 = onView(
            allOf(
                withId(R.id.main_fragment_nav), withContentDescription("Main"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_bar),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView5.perform(click())

        Thread.sleep(2000)


        val uiDevice: UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val context = ApplicationProvider.getApplicationContext<Context>()
        val packageName = context.packageName
        val editText = uiDevice.findObject(By.res(packageName, "input_edit_text"))
        editText.text = "robots"

        Thread.sleep(2000)

        val recyclerView = onView(
            allOf(
                withId(R.id.mainFragmentRecyclerView),
                childAtPosition(
                    withClassName(`is`("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                    1
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val textView = onView(
            allOf(
                withId(R.id.english_title), withText("Ninja Robots"),
                withParent(
                    allOf(
                        withId(R.id.main),
                        withParent(withId(R.id.details_scroll_view))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Ninja Robots")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
