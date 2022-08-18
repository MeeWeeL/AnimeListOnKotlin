package com.meeweel.anilist

import STEPS
import android.widget.TextView
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import org.junit.Assert
import org.junit.Test

class UiAutomatorOpenSettingsTest {

    private val uiDevice: UiDevice =
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @Test
    fun test_OpenSettings() {
        uiDevice.pressHome()
        uiDevice.swipe(500, 600, 500, 100, STEPS)
        val appViews = UiScrollable(UiSelector().scrollable(false))
        val className = UiSelector().className(TextView::class.java.name)
        val settingsApp = appViews.getChildByText(className, "Settings")
        settingsApp.clickAndWaitForNewWindow()
        val settingsValidation =
            uiDevice.findObject(UiSelector().packageName("com.android.settings"))
        Assert.assertTrue(settingsValidation.exists())
        uiDevice.swipe(500, 600, 500, 100, STEPS)
    }
}