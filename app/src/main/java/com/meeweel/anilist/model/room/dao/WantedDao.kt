package com.meeweel.anilist.model.room.dao

import androidx.room.*
import com.meeweel.anilist.model.room.entityes.WantedEntity

@Dao
interface WantedDao {

    @Query("SELECT * FROM WantedEntity")
    fun wanted(): List<WantedEntity>

    @Query("SELECT * FROM WantedEntity WHERE title LIKE :title")
    fun getWantedByWord(title: String): List<WantedEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun wantedInsert(wantedEntity: WantedEntity)

    @Update
    fun updateWantedEntity(wantedEntity: WantedEntity)

    @Query("DELETE FROM WantedEntity")
    fun deleteWantedEntity()
}