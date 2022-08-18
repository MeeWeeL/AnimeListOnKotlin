package com.meeweel.anilist.data.room

import com.meeweel.anilist.model.data.AnimeResponse
import com.meeweel.anilist.domain.models.Anime
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.data.repository.LocalRepository
import io.reactivex.rxjava3.core.Single
import java.lang.Exception

class LocalRepositoryImpl(
    private val localEntityDataSource: EntityDao
) : LocalRepository {

    override fun getQuantity(): Int {
        return localEntityDataSource.getQuantity()
    }

    override fun getAllAnime(): Single<List<ShortAnime>> {
        return localEntityDataSource.getAllAnime()
    }

    override fun getLocalMainAnimeList(): List<ShortAnime> {
        return localEntityDataSource.getShortAnimeList(1)
    }

    override fun getLocalWatchedAnimeList(): List<ShortAnime> {
        return localEntityDataSource.getShortAnimeList(2)
    }

    override fun getLocalNotWatchedAnimeList(): List<ShortAnime> {
        return localEntityDataSource.getShortAnimeList(3)
    }

    override fun getLocalWantedAnimeList(): List<ShortAnime> {
        return localEntityDataSource.getShortAnimeList(4)
    }

    override fun getLocalUnwantedAnimeList(): List<ShortAnime> {
        return localEntityDataSource.getShortAnimeList(5)
    }

    override fun insertLocalEntity(entity: Entity) {
        localEntityDataSource.insert(entity)
    }

    override fun insertLocalEntity(entityList: List<Entity>) {
        localEntityDataSource.insertList(entityList)
    }

    override fun updateLocalEntity(aniId: Int, list: Int) {
        localEntityDataSource.update(aniId, list)
    }

    override fun updateFromNetwork(anime: AnimeResponse, id: Int) {
        localEntityDataSource.updateFromNetwork(
            anime.ruTitle,
            anime.enTitle,
            anime.originalTitle,
            anime.ageRating,
            anime.ruDescription,
            anime.enDescription,
            anime.seriesQuantity,
            anime.image,
            calculateRating(
                anime.rating1,
                anime.rating2,
                anime.rating3,
                anime.rating4,
                anime.rating5
            ),
            anime.data,
            anime.ruGenre,
            anime.enGenre,
            anime.author,
            id
        )
    }

    private fun calculateRating(r1: Int, r2: Int, r3: Int, r4: Int, r5: Int): Int {
        return try {
            (r2 * 25 + r3 * 50 + r4 * 75 + r5 * 100) / (r1 + r2 + r3 + r4 + r5)
        } catch (e: Exception) {
            0
        }
    }

    override fun getAnimeById(id: Int): Anime {
        return localEntityDataSource.getEntityById(id).toModel()
    }

    override fun updateRate(id: Int, score: Int) {
        localEntityDataSource.updateRate(id, score)
    }
}