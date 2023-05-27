package com.meeweel.anilist.domain.useCases

import androidx.room.Room
import com.meeweel.anilist.app.App
import com.meeweel.anilist.data.repository.Repository
import com.meeweel.anilist.data.repository.RepositoryImpl
import com.meeweel.anilist.data.retrofit.RetrofitImpl
import com.meeweel.anilist.data.room.EntityDataBase
import com.meeweel.anilist.di.RepositoryModule
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.domain.enums.ListState

class GetAnimeListUseCase(
    private val repository: Repository = RepositoryImpl(
        RetrofitImpl.getService(),
        Room.databaseBuilder(
            App.ContextHolder.context,
            EntityDataBase::class.java,
            RepositoryModule.DB_NAME
        ).allowMainThreadQueries().build().entityDao()
    )
) {

    suspend operator fun invoke(state: ListState): List<ShortAnime> {
        return repository.getAnimeListLocal(state).sortedBy(ShortAnime::ruTitle)
    }
}