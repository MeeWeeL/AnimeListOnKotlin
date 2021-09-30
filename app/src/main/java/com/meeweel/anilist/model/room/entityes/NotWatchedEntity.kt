package com.meeweel.anilist.model.room.entityes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NotWatchedEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val image: Int,
    val data: String,
    val genre: String,
    val author: String
)
