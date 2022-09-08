package com.meeweel.anilist.data.interactors

import com.meeweel.anilist.data.repository.LocalRepository
import com.meeweel.anilist.data.repository.RemoteRepository
import com.meeweel.anilist.data.retrofit.AnimeApi
import com.meeweel.anilist.data.room.Entity
import com.meeweel.anilist.domain.models.Anime
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.model.data.AnimeResponse
import com.meeweel.anilist.model.data.MaxIdResponse
import io.reactivex.rxjava3.core.Single

class InteractorImpl(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) : Interactor {

    override fun getAnimeQuantity(): Single<Int> {
        return localRepository.getQuantity()
    }

    override fun getAllAnime(): Single<List<ShortAnime>> {
        return localRepository.getAllAnime()
    }

    override fun getLocalMainAnimeList(): Single<List<ShortAnime>> {
        return localRepository.getLocalMainAnimeList()
    }

    override fun getLocalWatchedAnimeList(): Single<List<ShortAnime>> {
        return localRepository.getLocalWatchedAnimeList()
    }

    override fun getLocalNotWatchedAnimeList(): Single<List<ShortAnime>> {
        return localRepository.getLocalNotWatchedAnimeList()
    }

    override fun getLocalWantedAnimeList(): Single<List<ShortAnime>> {
        return localRepository.getLocalWantedAnimeList()
    }

    override fun getLocalUnwantedAnimeList(): Single<List<ShortAnime>> {
        return localRepository.getLocalUnwantedAnimeList()
    }

    override fun insertLocalEntity(entity: Entity) {
        return localRepository.insertLocalEntity(entity)
    }

    override fun insertLocalEntity(entityList: List<Entity>) {
        return localRepository.insertLocalEntity(entityList)
    }

    override fun updateLocalEntity(aniId: Int, list: Int) {
        return localRepository.updateLocalEntity(aniId, list)
    }

    override fun updateFromNetwork(anime: AnimeResponse, id: Int) {
        return localRepository.updateFromNetwork(anime, id)
    }

    override fun getAnimeById(id: Int): Single<Anime> {
        return localRepository.getAnimeById(id)
    }

    override fun updateRate(id: Int, score: Int) {
        return localRepository.updateRate(id, score)
    }

    override fun getAnime(id: Int): Single<AnimeResponse> {
        return remoteRepository.getAnime(id)
    }

    override fun getAnimeList(id: Int): Single<List<AnimeResponse>> {
        return remoteRepository.getAnimes(id)
    }

    override fun getQuantity(): Single<MaxIdResponse> {
        return remoteRepository.getQuantity()
    }

    override fun getActualVersion(): Single<String> {
        return remoteRepository.getActualVersion()
    }

    override fun rateScore(score: Int, id: Int): Single<String> {
        return remoteRepository.rateScore(score, id)
    }
}