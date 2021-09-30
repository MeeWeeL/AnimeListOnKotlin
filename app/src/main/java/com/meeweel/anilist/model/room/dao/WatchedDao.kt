package com.meeweel.anilist.model.room.dao

import androidx.room.*
import com.meeweel.anilist.model.room.entityes.WatchedEntity

@Dao
interface WatchedDao {

    @Query("SELECT * FROM WatchedEntity")
    fun watched(): List<WatchedEntity>

    @Query("SELECT * FROM WatchedEntity WHERE title LIKE :title")
    fun getWatchedByWord(title: String): List<WatchedEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun watchedInsert(watchedEntity: WatchedEntity)

    @Update
    fun updateWatchedEntity(watchedEntity: WatchedEntity)

    @Query("DELETE FROM WatchedEntity")
    fun deleteWatchedEntity()
}