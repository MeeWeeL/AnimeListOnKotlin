package com.meeweel.anilist.model.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.meeweel.anilist.model.room.dao.WantedDao
import com.meeweel.anilist.model.room.entityes.WantedEntity

@Database(entities = arrayOf(WantedEntity::class), version = 1, exportSchema = false)
abstract class WantedDataBase : RoomDatabase() {

    abstract fun wantedDao(): WantedDao
}