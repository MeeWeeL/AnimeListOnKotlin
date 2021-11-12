package com.meeweel.anilist.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface AnimeApi {


    @GET("./api.php?q=post")
    @Headers("Content-type: application/json")
    fun getAnime(@Query("id") id: Int): Single<AnimeResponse> // @Query добавляет в запрос &id=$id

    @GET("./api.php?q=maxId")
    @Headers("Content-type: application/json")
    fun getQuantity(): Single<MaxIdResponse>
}