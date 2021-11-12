package com.meeweel.anilist.model.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.meeweel.anilist.model.room.dao.EntityDao
import com.meeweel.anilist.model.room.entityes.Entity

@Database(entities = arrayOf(Entity::class), version = 1, exportSchema = false)
abstract class EntityDataBase : RoomDatabase() {

    abstract fun entityDao(): EntityDao
}