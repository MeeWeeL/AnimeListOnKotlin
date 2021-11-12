package com.meeweel.anilist.model.data

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AniResp (
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
) : Parcelable