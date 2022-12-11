package com.meeweel.anilist.data.room

import com.meeweel.anilist.data.repository.LocalRepository
import com.meeweel.anilist.domain.models.Anime
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.model.data.AnimeResponse
import com.meeweel.anilist.ui.MainActivity
import io.reactivex.rxjava3.core.Single

class LocalRepositoryImpl(
    private val localEntityDataSource: EntityDao // мокать
) : LocalRepository {

    override fun getQuantity(): Single<Int> {
        return localEntityDataSource.getQuantity()
    }

    override fun getAllAnime(): Single<List<ShortAnime>> {
        return localEntityDataSource.getAllAnime()
    }

    override fun getLocalMainAnimeList(): Single<List<ShortAnime>> {
        return localEntityDataSource.getShortAnimeList(MainActivity.MAIN)
    }

    override fun getLocalWatchedAnimeList(): Single<List<ShortAnime>> {
        return localEntityDataSource.getShortAnimeList(MainActivity.WATCHED)
    }

    override fun getLocalNotWatchedAnimeList(): Single<List<ShortAnime>> {
        return localEntityDataSource.getShortAnimeList(MainActivity.NOT_WATCHED)
    }

    override fun getLocalWantedAnimeList(): Single<List<ShortAnime>> {
        return localEntityDataSource.getShortAnimeList(MainActivity.WANTED)
    }

    override fun getLocalUnwantedAnimeList(): Single<List<ShortAnime>> {
        return localEntityDataSource.getShortAnimeList(MainActivity.UNWANTED)
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

    override fun getAnimeById(id: Int): Single<Anime> {
        return localEntityDataSource.getEntityById(id).map { it.toModel() }
    }

    override fun updateRate(id: Int, score: Int) {
        localEntityDataSource.updateRate(id, score)
    }
}