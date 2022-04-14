package com.meeweel.anilist.model.repository

import com.meeweel.anilist.model.data.AnimeResponse
import com.meeweel.anilist.model.data.Anime
import com.meeweel.anilist.model.data.ShortAnime
import com.meeweel.anilist.model.room.Entity

interface LocalRepository {
    fun getQuantity(): Int
    fun getLocalMainAnimeList(): List<ShortAnime>
    fun getLocalWatchedAnimeList(): List<ShortAnime>
    fun getLocalNotWatchedAnimeList(): List<ShortAnime>
    fun getLocalWantedAnimeList(): List<ShortAnime>
    fun getLocalUnwantedAnimeList(): List<ShortAnime>
    fun insertLocalEntity(entity: Entity)
    fun updateLocalEntity(aniId: Int, list: Int)
    fun updateFromNetwork(anime: AnimeResponse, id: Int)
    fun getAnimeById(id: Int) : Anime
    fun updateRate(id: Int, score: Int)
    fun insertLocalEntity(entityList: List<Entity>)
}