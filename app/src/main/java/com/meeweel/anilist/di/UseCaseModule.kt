package com.meeweel.anilist.di

import android.provider.Telephony.Mms.Rate
import com.meeweel.anilist.domain.repository.Repository
import com.meeweel.anilist.domain.useCases.ChangeAnimeStateUseCase
import com.meeweel.anilist.domain.useCases.GetAnimeUseCase
import com.meeweel.anilist.domain.useCases.GetAnimeListUseCase
import com.meeweel.anilist.domain.useCases.RateAnimeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideGetAnimeListUseCase(repository: Repository): GetAnimeListUseCase {
        return GetAnimeListUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideChangeAnimeStateUseCase(repository: Repository): ChangeAnimeStateUseCase {
        return ChangeAnimeStateUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetAnimeByIdUseCase(repository: Repository): GetAnimeUseCase {
        return GetAnimeUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideRateAnimeUseCase(repository: Repository): RateAnimeUseCase {
        return RateAnimeUseCase(repository)
    }
}