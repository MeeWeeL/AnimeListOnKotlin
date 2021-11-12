package com.meeweel.anilist.model.repository

import com.meeweel.anilist.model.data.Anime
import com.meeweel.anilist.model.room.entityes.Entity

interface LocalRepository {
    fun getQuantity(): Int
    fun getLocalMainAnimeList(): List<Anime>
    fun getLocalWatchedAnimeList(): List<Anime>
    fun getLocalNotWatchedAnimeList(): List<Anime>
    fun getLocalWantedAnimeList(): List<Anime>
    fun getLocalUnwantedAnimeList(): List<Anime>
    fun insertLocalEntity(entity: Entity)
    fun updateLocalEntity(anime: Anime, list: Int)
}