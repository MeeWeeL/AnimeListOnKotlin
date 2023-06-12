package com.meeweel.anilist.di

import com.meeweel.anilist.data.sinchonizer.AnimeSynchronizer
import com.meeweel.anilist.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SynchronisationModule {
    @Singleton
    @Provides
    fun provideSynchronizer(repository: Repository): AnimeSynchronizer {
        return AnimeSynchronizer(repository)
    }
}