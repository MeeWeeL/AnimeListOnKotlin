package com.meeweel.anilist.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.meeweel.anilist.BuildConfig.API_key
import com.meeweel.anilist.di.AppComponent
import com.meeweel.anilist.di.DaggerAppComponent
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig

class App : Application() {

    @SuppressLint("StaticFieldLeak")
    object ContextHolder {
        lateinit var context: Context
    }

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        // Creating an extended library configuration.
        val config: YandexMetricaConfig = YandexMetricaConfig.newConfigBuilder(API_key).build()
        // Initializing the AppMetrica SDK.
        YandexMetrica.activate(applicationContext, config)
        // Automatic tracking of user activity.
        YandexMetrica.enableActivityAutoTracking(this)

        appInstance = this
        ContextHolder.context = this
        component = DaggerAppComponent.builder()
            .setContext(this)
            .build()
    }

    companion object {
        lateinit var appInstance: App
    }
}