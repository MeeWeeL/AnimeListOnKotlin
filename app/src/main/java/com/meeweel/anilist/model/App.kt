package com.meeweel.anilist.model

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.meeweel.anilist.di.AppComponent
import com.meeweel.anilist.di.DaggerAppComponent
import com.meeweel.anilist.model.retrofit.AnimeApi

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
    }

    companion object {
        lateinit var appInstance: App
    }
//    companion object {
//        @SuppressLint("StaticFieldLeak")
//        private var appInstance: App? = null
//        private var dbEntity: EntityDataBase? = null
//        private const val DB_WATCHED = "Repository.db"
//        private val cicerone: Cicerone<CustomRouter> by lazy {
//            Cicerone.create(CustomRouter())
//        }
//        val navigatorHolder = cicerone.getNavigatorHolder()
//        val appRouter = cicerone.router
//    }
}