package com.meeweel.anilist.di

import com.meeweel.anilist.domain.repository.Repository
import com.meeweel.anilist.data.retrofit.AnimeSynchronizer
import dagger.Module
import dagger.Provides

@Module
class SynchronizerModule {

    @Provides
    fun provideSynchronizer(repository: Repository): AnimeSynchronizer =
        AnimeSynchronizer(repository)
}