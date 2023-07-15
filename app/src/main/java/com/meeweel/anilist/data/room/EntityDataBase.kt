package com.meeweel.anilist.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Entity::class], version = 1, exportSchema = false)
abstract class EntityDataBase : RoomDatabase() {
    abstract fun entityDao(): EntityDao
}