package com.meeweel.anilist.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import com.meeweel.anilist.model.data.Anime
import com.meeweel.anilist.model.data.ShortAnime
import com.meeweel.anilist.model.repository.LocalRepository
import com.meeweel.anilist.model.repository.LocalRepositoryImpl
import com.meeweel.anilist.model.room.App.Companion.getEntityDao

@SuppressLint("StaticFieldLeak")
object Changing {
    private lateinit var theContext: Context
    private var roomDataBase: LocalRepository = LocalRepositoryImpl(getEntityDao())

    fun saveTo(aniId: Int, list: Int) {
        roomDataBase.updateLocalEntity(aniId, list)
    }

    fun setContext(context: Context) {
        theContext = context
    }

    fun getContext(): Context {
        return theContext
    }

    const val MAIN = 1
    const val WATCHED = 2
    const val NOT_WATCHED = 3
    const val WANTED = 4
    const val UNWANTED = 5
}