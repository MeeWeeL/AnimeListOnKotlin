package com.meeweel.anilist.model.repository

import com.meeweel.anilist.model.data.Anime

interface LocalRepository {
    fun getLocalWatchedAnimeList(): List<Anime>
    fun getLocalNotWatchedAnimeList(): List<Anime>
    fun getLocalWantedAnimeList(): List<Anime>
    fun getLocalUnwantedAnimeList(): List<Anime>
    fun saveLocalWatchedEntity(list: List<Anime>)
    fun saveLocalNotWatchedEntity(list: List<Anime>)
    fun saveLocalWantedEntity(list: List<Anime>)
    fun saveLocalUnwantedEntity(list: List<Anime>)
}