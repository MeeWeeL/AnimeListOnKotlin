package com.meeweel.anilist.data.retrofit

import com.meeweel.anilist.data.repository.RemoteRepository
import com.meeweel.anilist.model.data.AnimeResponse
import com.meeweel.anilist.model.data.MaxIdResponse
import io.reactivex.rxjava3.core.Single

class RemoteRepositoryImpl(
    private val remoteDataSource: AnimeApi
) : RemoteRepository {

    override fun getAnime(id: Int): Single<AnimeResponse> {
        return remoteDataSource.getAnime(id)
    }

    override fun getAnimes(id: Int): Single<List<AnimeResponse>> {
        return remoteDataSource.getAnimes(id)
    }

    override fun getQuantity(): Single<MaxIdResponse> {
        return remoteDataSource.getQuantity()
    }

    override fun getActualVersion(): Single<String> {
        return remoteDataSource.getActualVersion()
    }

    override fun rateScore(score: Int, id: Int): Single<String> {
        return remoteDataSource.rateScore(score, id)
    }
}