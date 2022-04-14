package com.meeweel.anilist.di

import com.meeweel.anilist.model.repository.LocalRepository
import com.meeweel.anilist.model.retrofit.AnimeApi
import com.meeweel.anilist.model.AnimeSynchronizer
import com.meeweel.anilist.model.rx.SearchSchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class SynchronizerModule {

    @Provides
    fun provideSynchronizer(aniApi: AnimeApi, repository: LocalRepository): AnimeSynchronizer =
        AnimeSynchronizer(aniApi, repository, SearchSchedulerProvider())
}