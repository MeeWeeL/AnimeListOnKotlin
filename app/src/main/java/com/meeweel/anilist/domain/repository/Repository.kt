package com.meeweel.anilist.domain.repository

import com.meeweel.anilist.data.room.Entity
import com.meeweel.anilist.domain.models.Anime
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.model.data.AnimeResponse
import com.meeweel.anilist.model.data.MaxIdResponse
import com.meeweel.anilist.domain.enums.ListState
import io.reactivex.rxjava3.core.Single

interface Repository {

    // RxJava
    fun getAnimeQuantityLocal(): Single<Int>
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
    fun getAnimeListRemote(id: Int): Single<List<AnimeResponse>>
    fun getQuantityRemote(): Single<MaxIdResponse>
    fun getActualVersionRemote(): Single<String>
    fun rateScoreRemote(score: Int, id: Int): Single<String>
}
