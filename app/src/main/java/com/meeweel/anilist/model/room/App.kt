package com.meeweel.anilist.model.room

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.meeweel.anilist.api.AnimeApi
import com.meeweel.anilist.model.room.dao.EntityDao
import com.meeweel.anilist.model.room.database.EntityDataBase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    lateinit var myContext: Context
    lateinit var animeApi: AnimeApi
    override fun onCreate() {
        super.onCreate()
        appInstance = this
        configureRetrofit()
        myContext = applicationContext
    }

    private fun configureRetrofit() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://anilist.pserver.ru")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        animeApi = retrofit.create(AnimeApi::class.java)
    }

    companion object {

        private var appInstance: App? = null
        private var dbEntity: EntityDataBase? = null
        private const val DB_WATCHED = "Repository.db"

        fun getEntityDao(): EntityDao {
            if (dbEntity == null) {
                synchronized(EntityDataBase::class.java) {
                    if (dbEntity == null) {
                        if (appInstance == null) throw IllegalStateException("Application is null while creating DataBase")
                        dbEntity = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            EntityDataBase::class.java,
                            DB_WATCHED
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }

            return dbEntity!!.entityDao()
        }
    }
}