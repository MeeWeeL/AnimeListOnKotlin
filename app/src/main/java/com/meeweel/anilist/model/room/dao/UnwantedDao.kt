package com.meeweel.anilist.model.room.dao

import androidx.room.*
import com.meeweel.anilist.model.room.entityes.UnwantedEntity

@Dao
interface UnwantedDao {

    @Query("SELECT * FROM UnwantedEntity")
    fun unwanted(): List<UnwantedEntity>

    @Query("SELECT * FROM UnwantedEntity WHERE title LIKE :title")
    fun getUnwantedByWord(title: String): List<UnwantedEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun unwantedInsert(unwantedEntity: UnwantedEntity)

    @Update
    fun updateUnwantedEntity(unwantedEntity: UnwantedEntity)

    @Query("DELETE FROM UnwantedEntity")
    fun deleteUnwantedEntity()
}