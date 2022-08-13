package com.meeweel.anilist.di

import android.content.Context
import androidx.room.Room
import com.meeweel.anilist.data.repository.LocalRepository
import com.meeweel.anilist.data.retrofit.AnimeApi
import com.meeweel.anilist.data.retrofit.RetrofitImpl
import com.meeweel.anilist.data.room.EntityDao
import com.meeweel.anilist.data.room.EntityDataBase
import com.meeweel.anilist.data.room.LocalRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    internal fun provideRepositoryLocal(dataSourceLocal: EntityDao):
            LocalRepository = LocalRepositoryImpl(dataSourceLocal)

    @Provides
    @Singleton
    internal fun provideDataSourceLocal(context: Context): EntityDao =
        Room.databaseBuilder(context, EntityDataBase::class.java, DB_NAME).allowMainThreadQueries().build().entityDao()


    @Provides
    @Singleton
    internal fun provideDataSourceRemote(): AnimeApi = RetrofitImpl().getService()

    companion object {
        const val DB_NAME = "Repository.db"
    }
}