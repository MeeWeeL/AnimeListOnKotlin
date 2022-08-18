package com.meeweel.anilist.data.repository

import com.meeweel.anilist.data.room.Entity
import com.meeweel.anilist.domain.models.Anime
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.model.data.AnimeResponse
import io.reactivex.rxjava3.core.Single

interface LocalRepository {
    fun getQuantity(): Int
    fun getAllAnime(): Single<List<ShortAnime>>
    fun getLocalMainAnimeList(): List<ShortAnime>
    fun getLocalWatchedAnimeList(): List<ShortAnime>
    fun getLocalNotWatchedAnimeList(): List<ShortAnime>
    fun getLocalWantedAnimeList(): List<ShortAnime>
    fun getLocalUnwantedAnimeList(): List<ShortAnime>
    fun insertLocalEntity(entity: Entity)
    fun updateLocalEntity(aniId: Int, list: Int)
    fun updateFromNetwork(anime: AnimeResponse, id: Int)
    fun getAnimeById(id: Int): Anime
    fun updateRate(id: Int, score: Int)
    fun insertLocalEntity(entityList: List<Entity>)
}