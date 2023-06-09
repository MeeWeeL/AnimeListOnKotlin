package com.meeweel.anilist.domain.useCases

import androidx.room.Room
import com.meeweel.anilist.app.App
import com.meeweel.anilist.data.repository.Repository
import com.meeweel.anilist.data.repository.RepositoryImpl
import com.meeweel.anilist.data.retrofit.RetrofitImpl
import com.meeweel.anilist.data.room.EntityDataBase
import com.meeweel.anilist.domain.models.Anime

class GetAnimeByIdUseCase(
    private val repository: Repository = RepositoryImpl(
        RetrofitImpl.getService(),
        Room.databaseBuilder(
            App.ContextHolder.context,
            EntityDataBase::class.java,
            "Repository.db"
        ).allowMainThreadQueries().build().entityDao()
    ),
) {
    suspend operator fun invoke(animeId: Int) : Anime {
        return repository.getAnimeByIdLocal(animeId)
    }
}