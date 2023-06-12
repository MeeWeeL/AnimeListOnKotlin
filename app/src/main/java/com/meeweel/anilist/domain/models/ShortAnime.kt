package com.meeweel.anilist.domain.models

data class ShortAnime(
    val id: Int,
    val ruTitle: String,
    val enTitle: String,
    val image: String,
    val data: String,
    val rating: Int,
    val enGenre: String,
    val ruGenre: String,
    var list: Int,
    val ratingCheck: Int
)