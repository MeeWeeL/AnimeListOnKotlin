package com.meeweel.anilist.model.repository

import com.meeweel.anilist.model.data.Anime
import com.meeweel.anilist.model.room.*
import com.meeweel.anilist.model.room.dao.NotWatchedDao
import com.meeweel.anilist.model.room.dao.UnwantedDao
import com.meeweel.anilist.model.room.dao.WantedDao
import com.meeweel.anilist.model.room.dao.WatchedDao

class LocalRepositoryImpl(
  private val localWatchedDataSource: WatchedDao,
  private val localNotWatchedDataSource: NotWatchedDao,
  private val localWantedDataSource: WantedDao,
  private val localUnwantedDataSource: UnwantedDao
  ) : LocalRepository {

    override fun getLocalWatchedAnimeList(): List<Anime> {
        return convertWatchedEntityToAnilist(localWatchedDataSource.watched())
    }

    override fun saveLocalWatchedEntity(list: List<Anime>) {
        localWatchedDataSource.deleteWatchedEntity()
        for (item in list) {
            localWatchedDataSource.watchedInsert(convertAnilistToWatchedEntity(item))
        }

    }

    override fun getLocalNotWatchedAnimeList(): List<Anime> {
        return convertNotWatchedEntityToAnilist(localNotWatchedDataSource.notWatched())
    }

    override fun saveLocalNotWatchedEntity(list: List<Anime>) {
        localNotWatchedDataSource.deleteNotWatchedEntity()
        for (item in list) {
            localNotWatchedDataSource.notWatchedInsert(convertAnilistToNotWatchedEntity(item))
        }

    }

    override fun getLocalWantedAnimeList(): List<Anime> {
        return convertWantedEntityToAnilist(localWantedDataSource.wanted())
    }

    override fun saveLocalWantedEntity(list: List<Anime>) {
        localWantedDataSource.deleteWantedEntity()
        for (item in list) {
            localWantedDataSource.wantedInsert(convertAnilistToWantedEntity(item))
        }

    }

    override fun getLocalUnwantedAnimeList(): List<Anime> {
        return convertUnwantedEntityToAnilist(localUnwantedDataSource.unwanted())
    }

    override fun saveLocalUnwantedEntity(list: List<Anime>) {
        localUnwantedDataSource.deleteUnwantedEntity()
        for (item in list) {
            localUnwantedDataSource.unwantedInsert(convertAnilistToUnwantedEntity(item))
        }

    }
}