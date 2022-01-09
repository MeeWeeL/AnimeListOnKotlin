package com.meeweel.anilist.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShortAnime(
    @SerializedName("id")
    val id: Int,
    @SerializedName("ruTitle")
    val ruTitle: String,
    @SerializedName("enTitle")
    val enTitle: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("data")
    val data: String,
    @SerializedName("rating")
    val rating: Int,
) : Parcelable