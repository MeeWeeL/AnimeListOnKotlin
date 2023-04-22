package com.meeweel.anilist.data.repository

import com.meeweel.anilist.data.retrofit.AnimeApi
import com.meeweel.anilist.data.room.Entity
import com.meeweel.anilist.data.room.EntityDao
import com.meeweel.anilist.data.room.toModel
import com.meeweel.anilist.domain.models.Anime
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.model.data.AnimeResponse
import com.meeweel.anilist.model.data.MaxIdResponse
import com.meeweel.anilist.newUI.ListState
import io.reactivex.rxjava3.core.Single

class RepositoryImpl(
    private val remoteDataSource: AnimeApi,
    private val localDataSource: EntityDao
) : Repository {

    // LOCAL
    // RxJava
    override fun getAnimeQuantityLocal(): Single<Int> {
        return localDataSource.getQuantity()
    }

    override fun getAllAnimeLocal(): Single<List<ShortAnime>> {
        return localDataSource.getAllAnime()
    }

    override fun getMainAnimeListLocal(): Single<List<ShortAnime>> {
        return localDataSource.getShortAnimeList(1)
    }

    override fun getWatchedAnimeListLocal(): Single<List<ShortAnime>> {
        return localDataSource.getShortAnimeList(2)
    }

    override fun getNotWatchedAnimeListLocal(): Single<List<ShortAnime>> {
        return localDataSource.getShortAnimeList(3)
    }

    override fun getWantedAnimeListLocal(): Single<List<ShortAnime>> {
        return localDataSource.getShortAnimeList(4)
    }

    override fun getUnwantedAnimeListLocal(): Single<List<ShortAnime>> {
        return localDataSource.getShortAnimeList(5)
    }

    override fun insertEntityLocal(entity: Entity) {
        return localDataSource.insert(entity)
    }

    override fun insertEntityLocal(entityList: List<Entity>) {
        return localDataSource.insertList(entityList)
    }

    override fun updateEntityLocal(aniId: Int, animeState: ListState) {
        return localDataSource.update(aniId, animeState.int)
    }

    override fun updateFromNetworkLocal(anime: AnimeResponse, id: Int) {
        return localDataSource.updateFromNetwork(
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

    override fun getAnimeByIdLocal(id: Int): Single<Anime> {
        return localDataSource.getEntityById(id).map { it.toModel() }
    }

    override fun updateRateLocal(id: Int, score: Int) {
        return localDataSource.updateRate(id, score)
    }

    // Coroutines
    override suspend fun getAnimeListLocal(listState: ListState): List<ShortAnime> {
        return localDataSource.getAnimeList(listState.int)
    }


    // REMOTE
    override suspend fun getAnimeByIdRemote(id: Int): AnimeResponse {
        return remoteDataSource.getAnimeByIdRemote(id)
    }

    override fun getAnimeRemote(id: Int): Single<AnimeResponse> {
        return remoteDataSource.getAnime(id)
    }

    override fun getAnimeListRemote(id: Int): Single<List<AnimeResponse>> {
        return remoteDataSource.getAnimes(id)
    }

    override fun getQuantityRemote(): Single<MaxIdResponse> {
        return remoteDataSource.getQuantity()
    }

    override fun getActualVersionRemote(): Single<String> {
        return remoteDataSource.getActualVersion()
    }

    override fun rateScoreRemote(score: Int, id: Int): Single<String> {
        return remoteDataSource.rateScore(score, id)
    }

    private fun calculateRating(r1: Int, r2: Int, r3: Int, r4: Int, r5: Int): Int {
        return try {
            (r2 * 25 + r3 * 50 + r4 * 75 + r5 * 100) / (r1 + r2 + r3 + r4 + r5)
        } catch (e: Exception) {
            0
        }
    }
}