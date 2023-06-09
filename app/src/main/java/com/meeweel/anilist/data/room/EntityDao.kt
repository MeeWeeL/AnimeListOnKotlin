package com.meeweel.anilist.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.meeweel.anilist.domain.models.ShortAnime
import io.reactivex.rxjava3.core.Single

@Dao
interface EntityDao {

    @Query("SELECT MAX(id) AS id FROM Entity")
    fun getQuantity(): Single<Int>

    @Query("SELECT id, ruTitle, enTitle, image, data, rating, enGenre, ruGenre, list, ratingCheck FROM Entity")
    fun getAllAnime(): Single<List<ShortAnime>>

    @Query("SELECT id, ruTitle, enTitle, image, data, rating, enGenre, ruGenre, list, ratingCheck FROM Entity WHERE list = :listState")
    suspend fun getAnimeList(listState: Int): List<ShortAnime>

    @Query("SELECT id, ruTitle, enTitle, image, data, rating, enGenre, ruGenre, list, ratingCheck FROM Entity WHERE list = :list")
    fun getShortAnimeList(list: Int): Single<List<ShortAnime>>

    @Query("UPDATE Entity SET ruTitle = :ruTitle, enTitle = :enTitle, originalTitle = :origTitle, ruDescription = :ruDescription, enDescription = :enDescription, image = :image, data = :data , ruGenre = :ruGenre, enGenre = :enGenre, author = :author, ageRating = :age, rating = :rating, seriesQuantity = :series WHERE id = :id")
    fun updateFromNetwork(
        ruTitle: String,
        enTitle: String,
        origTitle: String,
        age: Int,
        ruDescription: String,
        enDescription: String,
        series: Int,
        image: String,
        rating: Int,
        data: String,
        ruGenre: String,
        enGenre: String,
        author: String,
        id: Int
    )

    @Query("UPDATE Entity SET ratingCheck = :score WHERE id = :id")
    fun updateRate(id: Int, score: Int)

    @Query("SELECT * FROM Entity WHERE id LIKE :id")
    suspend fun getEntityById(id: Int): Entity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: Entity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertList(entity: List<Entity>)

    @Query("UPDATE Entity SET list = :list WHERE id = :id")
    fun update(id: Int, list: Int)

}