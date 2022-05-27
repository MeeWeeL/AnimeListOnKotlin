package com.meeweel.anilist.app

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.meeweel.anilist.di.AppComponent
import com.meeweel.anilist.di.DaggerAppComponent

class App : Application() {

    @SuppressLint("StaticFieldLeak")
    object ContextHolder { lateinit var context: Context }

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        ContextHolder.context = this
        component = DaggerAppComponent.builder()
            .setContext(this)
            .build()

        // For WorkManager
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "download_channel" // getString(R.string.channel_name)
            val descriptionText = "Synchronisation" // getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        lateinit var appInstance: App
    }
}