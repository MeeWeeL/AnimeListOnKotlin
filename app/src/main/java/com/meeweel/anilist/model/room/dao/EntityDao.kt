package com.meeweel.anilist.model.room.dao

import androidx.room.*
import com.meeweel.anilist.model.room.entityes.Entity

@Dao
interface EntityDao {

    @Query("SELECT MAX(id) AS id FROM Entity")
    fun getQuantity(): Int

    @Query("SELECT * FROM Entity WHERE list = 1")
    fun getMain(): List<Entity>

    @Query("SELECT * FROM Entity WHERE list = 2")
    fun getWatched(): List<Entity>

    @Query("SELECT * FROM Entity WHERE list = 3")
    fun getNotWatched(): List<Entity>

    @Query("SELECT * FROM Entity WHERE list = 4")
    fun getWanted(): List<Entity>

    @Query("SELECT * FROM Entity WHERE list = 5")
    fun getUnwanted(): List<Entity>

    @Query("SELECT * FROM Entity WHERE id LIKE :id")
    fun getEntityById(id: Int): Entity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(Entity: Entity)

    @Query("UPDATE Entity SET list = :list WHERE id = :id")
    fun update(id: Int, list: Int)

}