package com.meeweel.anilist.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Anime(
    val id: Int,
    val ruTitle: String,
    val enTitle: String,
    val originalTitle: String,
    val description: String,
    val image: String,
    val data: String,
    val genre: String,
    val author: String,
    val ageRating: Int,
    val rating: Int,
    val seriesQuantity: Int,
    val ratingCheck: Int,
    var list: Int
) : Parcelable