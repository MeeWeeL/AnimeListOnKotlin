package com.meeweel.anilist.model.repository

import com.meeweel.anilist.model.data.Anime

interface Repository {

    fun getAnimeFromServer(): Anime
    fun getAnimeFromLocalStorage(): List<Anime>
    fun getWatchedAnimeFromLocalStorage(): List<Anime>
    fun getNotWatchedAnimeFromLocalStorage(): List<Anime>
    fun getWantedAnimeFromLocalStorage(): List<Anime>
    fun getUnwantedAnimeFromLocalStorage(): List<Anime>
}