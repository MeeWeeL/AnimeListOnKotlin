package com.meeweel.anilist.data.repository

import com.meeweel.anilist.data.room.Entity
import com.meeweel.anilist.domain.models.Anime
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.model.data.AnimeResponse
import com.meeweel.anilist.model.data.MaxIdResponse
import io.reactivex.rxjava3.core.Single

interface Repository {

    fun getAnimeQuantityLocal(): Single<Int>
    fun getAllAnimeLocal(): Single<List<ShortAnime>>
    fun getMainAnimeListLocal(): Single<List<ShortAnime>>
    fun getWatchedAnimeListLocal(): Single<List<ShortAnime>>
    fun getNotWatchedAnimeListLocal(): Single<List<ShortAnime>>
    fun getWantedAnimeListLocal(): Single<List<ShortAnime>>
    fun getUnwantedAnimeListLocal(): Single<List<ShortAnime>>
    fun insertEntityLocal(entity: Entity)
    fun updateEntityLocal(aniId: Int, list: Int)
    fun updateFromNetworkLocal(anime: AnimeResponse, id: Int)
    fun getAnimeByIdLocal(id: Int): Single<Anime>
    fun updateRateLocal(id: Int, score: Int)
    fun insertEntityLocal(entityList: List<Entity>)

    fun getAnimeRemote(id: Int): Single<AnimeResponse>
    suspend fun getAnimeByIdRemote(id: Int): AnimeResponse
    fun getAnimeListRemote(id: Int): Single<List<AnimeResponse>>
    fun getQuantityRemote(): Single<MaxIdResponse>
    fun getActualVersionRemote(): Single<String>
    fun rateScoreRemote(score: Int, id: Int): Single<String>
}
