package com.meeweel.anilist.uiAutoTest.detailsFragment

import com.meeweel.anilist.uiAutoTest.detailsFragment.tests.DetailsScroll
import com.meeweel.anilist.uiAutoTest.detailsFragment.tests.DetailsTextVisible
import com.meeweel.anilist.uiAutoTest.mainFragment.cards.CardButtonsWork
import com.meeweel.anilist.uiAutoTest.mainFragment.cards.CardSwipesWork
import com.meeweel.anilist.uiAutoTest.mainFragment.navBar.CorrectTitlesOnNavPages
import com.meeweel.anilist.uiAutoTest.mainFragment.navBar.NavButtonsLabelsVisibility
import com.meeweel.anilist.uiAutoTest.mainFragment.navBar.StartSelectedNavButton
import com.meeweel.anilist.uiAutoTest.mainFragment.popupMenu.PopupVisibilityCorrect
import com.meeweel.anilist.uiAutoTest.mainFragment.popupMenu.PopupWork
import com.meeweel.anilist.uiAutoTest.mainFragment.profile.ProfileTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    DetailsTextVisible::class,
    DetailsScroll::class
)
class DetailsFragmentTest