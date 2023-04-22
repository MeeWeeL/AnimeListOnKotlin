package com.meeweel.anilist.uiAutoTest.tests

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.meeweel.anilist.EspressoUtils
import com.meeweel.anilist.EspressoUtils.delay
import com.meeweel.anilist.EspressoUtils.deleteDB
import com.meeweel.anilist.EspressoUtils.insertDB
import com.meeweel.anilist.newUI.NewMainActivity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.*


@RunWith(AndroidJUnit4::class)
class Test2002 {

    private lateinit var scenario: ActivityScenario<NewMainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(NewMainActivity::class.java)
        insertDB()
    }

    @Test
    fun fas() {
//        val time = System.currentTimeMillis()
//        while (System.currentTimeMillis() - time < 5000) {
        delay(4000)
            EspressoUtils.findViewByText("")
//        }
    }

    @After
    fun close() {
        deleteDB()
        scenario.close()
    }
}