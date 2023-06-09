package com.meeweel.anilist.domain.useCases

import androidx.room.Room
import com.meeweel.anilist.app.App
import com.meeweel.anilist.data.repository.Repository
import com.meeweel.anilist.data.repository.RepositoryImpl
import com.meeweel.anilist.data.retrofit.RetrofitImpl
import com.meeweel.anilist.data.room.EntityDataBase
import com.meeweel.anilist.di.RepositoryModule
import com.meeweel.anilist.domain.models.Anime
import com.meeweel.anilist.model.data.AnimeResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

class GetAnimeByIdUseCase(
    private val repository: Repository = RepositoryImpl(
        RetrofitImpl.getService(),
        Room.databaseBuilder(
            App.ContextHolder.context,
            EntityDataBase::class.java,
            RepositoryModule.DB_NAME
        ).allowMainThreadQueries().build().entityDao()
    ),
) {
    suspend operator fun invoke(animeId: Int) : AnimeResponse {
        return repository.getAnimeByIdRemote(animeId)
    }
}