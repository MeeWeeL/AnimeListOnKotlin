package com.meeweel.anilist.model.room

import android.app.Application
import androidx.room.Room
import com.meeweel.anilist.model.data.syncRepo
import com.meeweel.anilist.model.room.dao.NotWatchedDao
import com.meeweel.anilist.model.room.dao.UnwantedDao
import com.meeweel.anilist.model.room.dao.WantedDao
import com.meeweel.anilist.model.room.dao.WatchedDao
import com.meeweel.anilist.model.room.database.NotWatchedDataBase
import com.meeweel.anilist.model.room.database.UnwantedDataBase
import com.meeweel.anilist.model.room.database.WantedDataBase
import com.meeweel.anilist.model.room.database.WatchedDataBase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {

        private var appInstance: App? = null
        private var dbWatched: WatchedDataBase? = null
        private const val DB_WATCHED = "Repository.db"
        private var dbNotWatched: NotWatchedDataBase? = null
        private const val DB_TON_WATCHED = "NotWatched.db"
        private var dbWanted: WantedDataBase? = null
        private const val DB_WANTED = "Wanted.db"
        private var dbUnwanted: UnwantedDataBase? = null
        private const val DB_UNWANTED = "Unwanted.db"

        fun getWatchedDao(): WatchedDao {
            if (dbWatched == null) {
                synchronized(WatchedDataBase::class.java) {
                    if (dbWatched == null) {
                        if (appInstance == null) throw IllegalStateException("Application is null while creating DataBase")
                        dbWatched = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            WatchedDataBase::class.java,
                            DB_WATCHED)
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }

            return dbWatched!!.watchedDao()
        }

        fun getNotWatchedDao(): NotWatchedDao {
            if (dbNotWatched == null) {
                synchronized(NotWatchedDataBase::class.java) {
                    if (dbNotWatched == null) {
                        if (appInstance == null) throw IllegalStateException("Application is null while creating DataBase")
                        dbNotWatched = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            NotWatchedDataBase::class.java,
                            DB_TON_WATCHED)
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }

            return dbNotWatched!!.notWatchedDao()
        }

        fun getWantedDao(): WantedDao {
            if (dbWanted == null) {
                synchronized(WantedDataBase::class.java) {
                    if (dbWanted == null) {
                        if (appInstance == null) throw IllegalStateException("Application is null while creating DataBase")
                        dbWanted = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            WantedDataBase::class.java,
                            DB_WANTED)
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }

            return dbWanted!!.wantedDao()
        }

        fun getUnwantedDao(): UnwantedDao {
            if (dbUnwanted == null) {
                synchronized(UnwantedDataBase::class.java) {
                    if (dbUnwanted == null) {
                        if (appInstance == null) throw IllegalStateException("Application is null while creating DataBase")
                        dbUnwanted = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            UnwantedDataBase::class.java,
                            DB_UNWANTED)
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }

            return dbUnwanted!!.unwantedDao()
        }
    }
}