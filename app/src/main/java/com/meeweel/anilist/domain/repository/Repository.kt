package com.meeweel.anilist.domain.repository

import com.meeweel.anilist.data.room.Entity
import com.meeweel.anilist.domain.models.Anime
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.data.retrofit.AnimeResponse
import com.meeweel.anilist.data.retrofit.MaxIdResponse
import com.meeweel.anilist.domain.enums.ListState
import io.reactivex.rxjava3.core.Single

interface Repository {

    // RxJava
    fun getAnimeQuantityLocal(): Int
    fun getAllAnimeLocal(): Single<List<ShortAnime>>
    fun getMainAnimeListLocal(): Single<List<ShortAnime>>
    fun getWatchedAnimeListLocal(): Single<List<ShortAnime>>
    fun getNotWatchedAnimeListLocal(): Single<List<ShortAnime>>
    fun getWantedAnimeListLocal(): Single<List<ShortAnime>>
    fun getUnwantedAnimeListLocal(): Single<List<ShortAnime>>
    fun insertEntityLocal(entity: Entity)
    fun updateEntityLocal(aniId: Int, animeState: ListState)
    fun updateFromNetworkLocal(anime: AnimeResponse, id: Int)
    suspend fun getAnimeLocal(id: Int): Anime
    fun updateRateLocal(id: Int, score: Int)
    fun insertEntityLocal(entityList: List<Entity>)

    // Coroutines
    suspend fun getAnimeListLocal(listState: ListState): List<ShortAnime>

    fun getAnimeRemote(id: Int): Single<AnimeResponse>
    suspend fun getAnimeByIdRemote(id: Int): AnimeResponse
    suspend fun getAnimeListRemote(id: Int): List<AnimeResponse>
    suspend fun getQuantityRemote(): MaxIdResponse
    suspend fun getActualVersionRemote(): String
    fun rateScoreRemote(score: Int, id: Int): Single<String>
}
