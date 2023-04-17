package com.meeweel.anilist.uiAutoTest

import com.meeweel.anilist.uiAutoTest.tests.Test2001
import com.meeweel.anilist.uiAutoTest.tests.Test2003
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    Test2001::class,
    Test2003::class
)
class FullTest