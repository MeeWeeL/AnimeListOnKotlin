package com.meeweel.anilist.data.interactors

import com.meeweel.anilist.data.room.Entity
import com.meeweel.anilist.domain.models.Anime
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.model.data.AnimeResponse
import com.meeweel.anilist.model.data.MaxIdResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Interactor {

    fun getAnimeQuantity(): Single<Int>
    fun getAllAnime(): Single<List<ShortAnime>>
    fun getLocalMainAnimeList(): Single<List<ShortAnime>>
    fun getLocalWatchedAnimeList(): Single<List<ShortAnime>>
    fun getLocalNotWatchedAnimeList(): Single<List<ShortAnime>>
    fun getLocalWantedAnimeList(): Single<List<ShortAnime>>
    fun getLocalUnwantedAnimeList(): Single<List<ShortAnime>>
    fun insertLocalEntity(entity: Entity)
    fun updateLocalEntity(aniId: Int, list: Int)
    fun updateFromNetwork(anime: AnimeResponse, id: Int)
    fun getAnimeById(id: Int): Single<Anime>
    fun updateRate(id: Int, score: Int)
    fun insertLocalEntity(entityList: List<Entity>)

    fun getAnime(id: Int): Single<AnimeResponse> // @Query добавляет в запрос &id=$id
    fun getAnimeList(id: Int): Single<List<AnimeResponse>>
    fun getQuantity(): Single<MaxIdResponse>
    fun getActualVersion(): Single<String>
    fun rateScore(score: Int, id: Int): Single<String>
}
