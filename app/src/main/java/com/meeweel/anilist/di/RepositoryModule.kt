package com.meeweel.anilist.di

import com.meeweel.anilist.data.repository.RepositoryImpl
import com.meeweel.anilist.data.retrofit.AnimeApi
import com.meeweel.anilist.data.room.EntityDao
import com.meeweel.anilist.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(api: AnimeApi, room: EntityDao): Repository {
        return RepositoryImpl(api, room)
    }
}