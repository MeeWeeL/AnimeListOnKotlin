package com.meeweel.anilist.model.repository

import com.meeweel.anilist.model.data.*

class RepositoryImpl : Repository {

    override fun getAnimeFromServer() = Anime()
    override fun getAnimeFromLocalStorage(): List<Anime> = getRepo()
    override fun getWatchedAnimeFromLocalStorage(): List<Anime> = getWatchedRepo()
    override fun getNotWatchedAnimeFromLocalStorage(): List<Anime> = getNotWatchedRepo()
    override fun getWantedAnimeFromLocalStorage(): List<Anime> = getWantedRepo()
    override fun getUnwantedAnimeFromLocalStorage(): List<Anime> = getUnwantedRepo()
}