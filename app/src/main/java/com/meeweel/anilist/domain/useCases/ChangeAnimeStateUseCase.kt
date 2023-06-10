package com.meeweel.anilist.domain.useCases

import androidx.room.Room
import com.meeweel.anilist.app.App
import com.meeweel.anilist.data.repository.Repository
import com.meeweel.anilist.data.repository.RepositoryConst
import com.meeweel.anilist.data.repository.RepositoryImpl
import com.meeweel.anilist.data.retrofit.RetrofitImpl
import com.meeweel.anilist.data.room.EntityDataBase
import com.meeweel.anilist.domain.enums.ListState

class ChangeAnimeStateUseCase(
    private val repository: Repository = RepositoryImpl(
        RetrofitImpl.getService(),
        Room.databaseBuilder(
            App.ContextHolder.context,
            EntityDataBase::class.java,
            RepositoryConst.DB_NAME
        ).allowMainThreadQueries().build().entityDao()
    ),
) {

    suspend operator fun invoke(animeId: Int, newState: ListState) {
        repository.updateEntityLocal(animeId, newState)
    }
}