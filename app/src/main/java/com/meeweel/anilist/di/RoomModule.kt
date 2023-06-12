package com.meeweel.anilist.di

import androidx.room.Room
import com.meeweel.anilist.app.App
import com.meeweel.anilist.data.room.EntityDao
import com.meeweel.anilist.data.room.EntityDataBase
import com.meeweel.anilist.data.room.RepositoryConst
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideRoom(): EntityDao {
        return Room.databaseBuilder(
            App.ContextHolder.context,
            EntityDataBase::class.java,
            RepositoryConst.DB_NAME
        ).allowMainThreadQueries().build().entityDao()
    }
}