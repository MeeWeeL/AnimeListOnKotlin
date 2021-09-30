package com.meeweel.anilist.model.room

import com.meeweel.anilist.model.data.Anime
import com.meeweel.anilist.model.room.entityes.NotWatchedEntity
import com.meeweel.anilist.model.room.entityes.UnwantedEntity
import com.meeweel.anilist.model.room.entityes.WantedEntity
import com.meeweel.anilist.model.room.entityes.WatchedEntity


fun convertWatchedEntityToAnilist(entityList: List<WatchedEntity>): List<Anime> {
    return entityList.map {
        Anime(it.title, it.description, it.image, it.data, it.genre, it.author)
    }
}

fun convertAnilistToWatchedEntity(anime: Anime): WatchedEntity {
    return WatchedEntity(
        0, anime.title, anime.description, anime.image, anime.data, anime.genre, anime.author)
}

fun convertNotWatchedEntityToAnilist(entityList: List<NotWatchedEntity>): List<Anime> {
    return entityList.map {
        Anime(it.title, it.description, it.image, it.data, it.genre, it.author)
    }
}

fun convertAnilistToNotWatchedEntity(anime: Anime): NotWatchedEntity {
    return NotWatchedEntity(
        0, anime.title, anime.description, anime.image, anime.data, anime.genre, anime.author)
}

fun convertWantedEntityToAnilist(entityList: List<WantedEntity>): List<Anime> {
    return entityList.map {
        Anime(it.title, it.description, it.image, it.data, it.genre, it.author)
    }
}

fun convertAnilistToWantedEntity(anime: Anime): WantedEntity {
    return WantedEntity(
        0, anime.title, anime.description, anime.image, anime.data, anime.genre, anime.author)
}

fun convertUnwantedEntityToAnilist(entityList: List<UnwantedEntity>): List<Anime> {
    return entityList.map {
        Anime(it.title, it.description, it.image, it.data, it.genre, it.author)
    }
}

fun convertAnilistToUnwantedEntity(anime: Anime): UnwantedEntity {
    return UnwantedEntity(
        0, anime.title, anime.description, anime.image, anime.data, anime.genre, anime.author)
}