package com.meeweel.anilist.model.data

import android.os.Parcelable
import com.meeweel.anilist.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class Anime (
    val title: String = "Anime",
    val description: String = "Description",
    val image: Int = R.drawable.anig,
    val data: String = "25.07.21",
    val genre: String = "Isekai",
    val author: String = "Shikamura Taisake"
) : Parcelable