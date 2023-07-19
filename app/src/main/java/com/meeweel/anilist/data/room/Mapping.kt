package com.meeweel.anilist.data.room

import com.meeweel.anilist.R
import com.meeweel.anilist.app.App
import com.meeweel.anilist.domain.models.Anime
import com.meeweel.anilist.data.retrofit.AnimeResponse

val isRussian: Boolean = App.ContextHolder.context.resources.getBoolean(R.bool.isRussian)

fun AnimeResponse.toEntity() = Entity(
    id = id,
    ruTitle = ruTitle,
    enTitle = enTitle,
    originalTitle = originalTitle,
    ruDescription = ruDescription,
    enDescription = enDescription,
//                getImageName(item.image),
    image = image,
    data = data,
    ruGenre = ruGenre,
    enGenre = enGenre,
    author = author,
    ageRating = ageRating,
    getRating(this),
    seriesQuantity = seriesQuantity,
    0,
    1
)

fun List<AnimeResponse>.toEntityList() = this.map { it.toEntity() }

private fun getRating(anime: AnimeResponse): Int {
    return try {
        (anime.rating2 * 25 + anime.rating3 * 50 + anime.rating4 * 75 +
                anime.rating5 * 100) / (anime.rating1 + anime.rating2 +
                anime.rating3 + anime.rating4 + anime.rating5)
    } catch (e: Exception) {
        0
    }
}

fun Entity.toModel() = Anime(
    id = id,
    ruTitle = ruTitle,
    enTitle = enTitle,
    originalTitle = originalTitle,
    description = if (isRussian) {
        ruDescription
    } else {
        enDescription
    },
    image = image,
    data = data,
    genre = if (isRussian) {
        ruGenre
    } else {
        enGenre
    },
    author = author,
    ageRating = ageRating,
    rating = rating,
    seriesQuantity = seriesQuantity,
    ratingCheck = ratingCheck,
    list = list
)
