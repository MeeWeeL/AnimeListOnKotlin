package com.meeweel.anilist.uiAutoTest.mainFragment

import com.meeweel.anilist.uiAutoTest.mainFragment.tests.CardButtonsWork
import com.meeweel.anilist.uiAutoTest.mainFragment.tests.CardSwipesWork
import com.meeweel.anilist.uiAutoTest.mainFragment.tests.StartSelectedNavButton
import com.meeweel.anilist.uiAutoTest.mainFragment.tests.CorrectTitlesOnNavPages
import com.meeweel.anilist.uiAutoTest.mainFragment.tests.NavButtonsLabelsVisibility
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    StartSelectedNavButton::class,
    CorrectTitlesOnNavPages::class,
    NavButtonsLabelsVisibility::class,
    CardButtonsWork::class,
    CardSwipesWork::class
)
class MainFragmentTest