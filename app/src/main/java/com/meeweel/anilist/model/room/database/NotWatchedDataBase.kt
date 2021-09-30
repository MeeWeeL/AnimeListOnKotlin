package com.meeweel.anilist.model.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.meeweel.anilist.model.room.dao.NotWatchedDao
import com.meeweel.anilist.model.room.entityes.NotWatchedEntity

@Database(entities = arrayOf(NotWatchedEntity::class), version = 1, exportSchema = false)
abstract class NotWatchedDataBase : RoomDatabase() {

    abstract fun notWatchedDao(): NotWatchedDao
}