package com.meeweel.anilist.uiAutoTest.mainFragment

import com.meeweel.anilist.uiAutoTest.mainFragment.cards.CardButtonsWork
import com.meeweel.anilist.uiAutoTest.mainFragment.cards.CardSwipesWork
import com.meeweel.anilist.uiAutoTest.mainFragment.navBar.StartSelectedNavButton
import com.meeweel.anilist.uiAutoTest.mainFragment.navBar.CorrectTitlesOnNavPages
import com.meeweel.anilist.uiAutoTest.mainFragment.navBar.NavButtonsLabelsVisibility
import com.meeweel.anilist.uiAutoTest.mainFragment.popupMenu.PopupVisibilityCorrect
import com.meeweel.anilist.uiAutoTest.mainFragment.popupMenu.PopupWork
import com.meeweel.anilist.uiAutoTest.mainFragment.profile.ProfileTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    StartSelectedNavButton::class,
    CorrectTitlesOnNavPages::class,
    NavButtonsLabelsVisibility::class,
    CardButtonsWork::class,
    CardSwipesWork::class,
    PopupVisibilityCorrect::class,
    PopupWork::class,
    ProfileTest::class
)
class MainFragmentTest