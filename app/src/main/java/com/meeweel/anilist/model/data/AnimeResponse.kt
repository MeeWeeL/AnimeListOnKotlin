package com.meeweel.anilist.model.data

import com.google.gson.annotations.SerializedName

data class MaxIdResponse(val id: Int)

data class AnimeResponse(
    val id: Int,
    val ruTitle: String,
    val enTitle: String,
    val originalTitle: String,
    // Если переменная не совпадает с названием в гсон файле, тогда
    // необходима аннотация с указанием названия переменной в гсоне
    //
    @SerializedName("ruDescription")
    val ruDescription: String,
    val enDescription: String,
    val image: String,
    val data: String,
    val ruGenre: String,
    val enGenre: String,
    val author: String,
    val ageRating: Int,
    val rating1: Int,
    val rating2: Int,
    val rating3: Int,
    val rating4: Int,
    val rating5: Int,
    val seriesQuantity: Int,
)