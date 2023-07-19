package com.meeweel.anilist.data.retrofit

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface AnimeApi {

    @GET("./api.php?q=post")
    @Headers("Content-type: application/json")
    fun getAnime(@Query("id") id: Int): Single<AnimeResponse> // @Query добавляет в запрос &id=$id

    @GET("./api.php?q=post")
    @Headers("Content-type: application/json")
    suspend fun getAnimeByIdRemote(@Query("id") id: Int): AnimeResponse

    @GET("./api.php?q=posts")
    @Headers("Content-type: application/json")
    suspend fun getAnimes(@Query("id") id: Int): List<AnimeResponse>

    @GET("./api.php?q=maxId")
    @Headers("Content-type: application/json")
    suspend fun getQuantity(): MaxIdResponse

    @GET("./api.php?q=actual_version")
    @Headers("Content-type: application/json")
    suspend fun getActualVersion(): String

    @GET("./api.php?q=score")
    fun rateScore(@Query("score") score: Int, @Query("id") id: Int): Single<String>
}