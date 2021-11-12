package com.meeweel.anilist.model.repository

import com.meeweel.anilist.model.data.Anime
import com.meeweel.anilist.model.room.*
import com.meeweel.anilist.model.room.dao.EntityDao
import com.meeweel.anilist.model.room.entityes.Entity

class LocalRepositoryImpl(
    private val localEntityDataSource: EntityDao
  ) : LocalRepository {

    override fun getQuantity(): Int {
        return localEntityDataSource.getQuantity()
    }

    override fun getLocalMainAnimeList(): List<Anime> {
        return convertEntityToAnilist(localEntityDataSource.getMain())
    }

    override fun getLocalWatchedAnimeList(): List<Anime> {
        return convertEntityToAnilist(localEntityDataSource.getWatched())
    }

    override fun getLocalNotWatchedAnimeList(): List<Anime> {
        return convertEntityToAnilist(localEntityDataSource.getNotWatched())
    }

    override fun getLocalWantedAnimeList(): List<Anime> {
        return convertEntityToAnilist(localEntityDataSource.getWanted())
    }

    override fun getLocalUnwantedAnimeList(): List<Anime> {
        return convertEntityToAnilist(localEntityDataSource.getUnwanted())
    }

    override fun insertLocalEntity(entity: Entity) {
        localEntityDataSource.insert(entity)
    }

    override fun updateLocalEntity(anime: Anime, list: Int) {
        localEntityDataSource.update(anime.id, list)
    }
}