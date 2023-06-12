package com.meeweel.anilist.di

import com.meeweel.anilist.data.retrofit.AnimeApi
import com.meeweel.anilist.data.retrofit.RetrofitImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun provideAnimeApi(): AnimeApi {
        return RetrofitImpl.getService()
    }
}