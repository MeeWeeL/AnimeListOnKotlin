package com.meeweel.anilist.model.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.meeweel.anilist.model.room.dao.WatchedDao
import com.meeweel.anilist.model.room.entityes.WatchedEntity

@Database(entities = arrayOf(WatchedEntity::class), version = 1, exportSchema = false)
abstract class WatchedDataBase : RoomDatabase() {

    abstract fun watchedDao(): WatchedDao
}