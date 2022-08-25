package com.meeweel.anilist.di

import com.meeweel.anilist.data.repository.LocalRepository
import com.meeweel.anilist.data.retrofit.AnimeApi
import com.meeweel.anilist.data.retrofit.AnimeSynchronizer
import com.meeweel.anilist.data.rx.SearchSchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class SynchronizerModule {

    @Provides
    fun provideSynchronizer(aniApi: AnimeApi, repository: LocalRepository): AnimeSynchronizer =
        AnimeSynchronizer(aniApi, repository, SearchSchedulerProvider())
}