package com.meeweel.anilist.model.room.dao

import androidx.room.*
import com.meeweel.anilist.model.room.entityes.NotWatchedEntity

@Dao
interface NotWatchedDao {

    @Query("SELECT * FROM NotWatchedEntity")
    fun notWatched(): List<NotWatchedEntity>

    @Query("SELECT * FROM NotWatchedEntity WHERE title LIKE :title")
    fun getNotWatchedByWord(title: String): List<NotWatchedEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun notWatchedInsert(notWatchedEntity: NotWatchedEntity)

    @Update
    fun updateNotWatchedEntity(notWatchedEntity: NotWatchedEntity)

    @Query("DELETE FROM NotWatchedEntity")
    fun deleteNotWatchedEntity()
}