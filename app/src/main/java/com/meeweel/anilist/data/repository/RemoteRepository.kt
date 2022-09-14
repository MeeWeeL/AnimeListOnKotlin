package com.meeweel.anilist.data.repository

import com.meeweel.anilist.model.data.AnimeResponse
import com.meeweel.anilist.model.data.MaxIdResponse
import io.reactivex.rxjava3.core.Single

interface RemoteRepository {

    fun getAnime(id: Int): Single<AnimeResponse> // @Query добавляет в запрос &id=$id
    fun getAnimes(id: Int): Single<List<AnimeResponse>>
    fun getQuantity(): Single<MaxIdResponse>
    fun getActualVersion(): Single<String>
    fun rateScore(score: Int, id: Int): Single<String>
}