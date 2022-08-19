package com.meeweel.anilist.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.meeweel.anilist.di.AppComponent
import com.meeweel.anilist.di.DaggerAppComponent

class App : Application() {

    @SuppressLint("StaticFieldLeak")
    object ContextHolder {
        lateinit var context: Context
    }

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
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