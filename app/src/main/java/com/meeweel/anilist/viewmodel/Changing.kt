package com.meeweel.anilist.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import com.meeweel.anilist.model.data.Anime
import com.meeweel.anilist.model.repository.LocalRepository
import com.meeweel.anilist.model.repository.LocalRepositoryImpl
import com.meeweel.anilist.model.room.App.Companion.getEntityDao

@SuppressLint("StaticFieldLeak")
object Changing {
    private lateinit var theContext: Context
    private lateinit var theActivity: Activity
    var roomDataBase: LocalRepository = LocalRepositoryImpl(getEntityDao())
    fun saveTo(anime: Anime, list: Int) {
        roomDataBase.updateLocalEntity(anime, list)
    }
    fun setContext(context: Context) {
        theContext = context
    }
    fun setActivity(activity: Activity) {
        theActivity = activity
    }
    fun getActivity() : Activity {
        return theActivity
    }
    fun getContext() : Context {
        return theContext
    }
}