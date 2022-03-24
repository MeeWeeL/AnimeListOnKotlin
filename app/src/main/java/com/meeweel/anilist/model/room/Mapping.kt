package com.meeweel.anilist.model.room

import com.meeweel.anilist.R
import com.meeweel.anilist.model.App
import com.meeweel.anilist.model.data.AnimeResponse
import com.meeweel.anilist.model.data.Anime

val isRussian: Boolean = App.ContextHolder.context.resources.getBoolean(R.bool.isRussian)
fun convertResponseToEntity(response: AnimeResponse) : Entity {
    return Entity(
        response.id,
        response.ruTitle,
        response.enTitle,
        response.originalTitle,
        response.ruDescription,
        response.enDescription,
//                getImageName(item.image),
        response.image,
        response.data,
        response.ruGenre,
        response.enGenre,
        response.author,
        response.ageRating,
        getRating(response),
        response.seriesQuantity,
        0,
        1
    )
}
fun convertResponseListToEntityList(list: List<AnimeResponse>) : List<Entity> {
    return list.map {
        Entity(
            it.id,
            it.ruTitle,
            it.enTitle,
            it.originalTitle,
            it.ruDescription,
            it.enDescription,
//                getImageName(item.image),
            it.image,
            it.data,
            it.ruGenre,
            it.enGenre,
            it.author,
            it.ageRating,
            getRating(it),
            it.seriesQuantity,
            0,
            1
        )
    }
}

private fun getRating(anime: AnimeResponse): Int {
    return try {
        (anime.rating2 * 25 + anime.rating3 * 50 + anime.rating4 * 75 +
                anime.rating5 * 100) / (anime.rating1 + anime.rating2 +
                anime.rating3 + anime.rating4 + anime.rating5)
    } catch (e: Exception) {
        0
    }
}

fun convertEntityToAnilist(entityList: List<Entity>): List<Anime> {
    return entityList.map {
        Anime(
            it.id, it.ruTitle, it.enTitle, it.originalTitle, if (isRussian) {
                it.ruDescription
            } else {
                it.enDescription
            },
            it.image, it.data, if (isRussian) {
                it.ruGenre
            } else {
                it.enGenre
            }, it.author, it.ageRating, it.rating, it.seriesQuantity, it.ratingCheck, it.list
        )
    }
}
fun convertEntityToAnime(it: Entity) : Anime {
    return Anime(
        it.id, it.ruTitle, it.enTitle, it.originalTitle, if (isRussian) {
            it.ruDescription
        } else {
            it.enDescription
        },
        it.image, it.data, if (isRussian) {
            it.ruGenre
        } else {
            it.enGenre
        }, it.author, it.ageRating, it.rating, it.seriesQuantity, it.ratingCheck, it.list
    )
}
//fun convertResponseToAnime(anime: AnimeResponse) : Anime {
//    return Anime(
//
//    )
//}