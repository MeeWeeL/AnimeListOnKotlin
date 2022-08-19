package com.meeweel.anilist.data.retrofit

import com.meeweel.anilist.model.data.AnimeResponse
import com.meeweel.anilist.model.data.MaxIdResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface AnimeApi {

    @GET("./api.php?q=post")
    @Headers("Content-type: application/json")
    fun getAnime(@Query("id") id: Int): Single<AnimeResponse> // @Query добавляет в запрос &id=$id

    @GET("./api.php?q=posts")
    @Headers("Content-type: application/json")
    fun getAnimes(@Query("id") id: Int): Single<List<AnimeResponse>>

    @GET("./api.php?q=maxId")
    @Headers("Content-type: application/json")
    fun getQuantity(): Single<MaxIdResponse>

    @GET("./api.php?q=score")
    fun reteScore(@Query("score") score: Int, @Query("id") id: Int): Single<String>
}