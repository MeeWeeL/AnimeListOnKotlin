package com.meeweel.anilist.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Entity(

    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val ruTitle: String,
    val enTitle: String,
    val originalTitle: String,
    val ruDescription: String,
    val enDescription: String,
    val image: String,
    val data: String,
    val ruGenre: String,
    val enGenre: String,
    val author: String,
    val ageRating: Int,
    val rating: Int,
    val seriesQuantity: Int,
    val ratingCheck: Int,
    var list: Int
)
