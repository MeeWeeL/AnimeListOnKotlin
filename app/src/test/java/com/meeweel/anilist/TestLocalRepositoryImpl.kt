package com.meeweel.anilist


import com.meeweel.anilist.data.room.Entity
import com.meeweel.anilist.data.room.EntityDao
import com.meeweel.anilist.data.room.LocalRepositoryImpl
import com.meeweel.anilist.data.room.toModel
import com.meeweel.anilist.domain.models.Anime
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.model.data.AnimeResponse
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.rxjava3.core.Single
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class TestLocalRepositoryImpl {

    private lateinit var repository: LocalRepositoryImpl

    @Mock
    private lateinit var dao: EntityDao

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = LocalRepositoryImpl(dao)
    }

    @Test
    fun getQuantity_Verify() {
        `when`(dao.getQuantity()).thenReturn(Single.just(1))
        repository.getQuantity()
        verify(dao, atLeastOnce()).getQuantity()
    }

    @Test
    fun getQuantity_NotNull() {
        `when`(dao.getQuantity()).thenReturn(Single.just(1))
        val quantity = repository.getQuantity()
        assertNotNull(quantity)
    }

    @Test
    fun getAllAnime_Verify() {
        val list = listOf<ShortAnime>()
        `when`(dao.getAllAnime()).thenReturn(Single.just(list))
        repository.getAllAnime()
        verify(dao, atLeastOnce()).getAllAnime()
    }

    @Test
    fun getAllAnime_NotNull() {
        val list = listOf<ShortAnime>()
        `when`(dao.getAllAnime()).thenReturn(Single.just(list))
        val repoAnimeList = repository.getAllAnime()
        assertNotNull(repoAnimeList)
    }

    @Test
    fun getLocalMainAnimeList_Verify() {
        val mainList = listOf<ShortAnime>()
        `when`(dao.getShortAnimeList(1)).thenReturn(Single.just(mainList))
        repository.getLocalMainAnimeList()
        verify(dao, atLeastOnce()).getShortAnimeList(1)
    }

    @Test
    fun getLocalMainAnimeList_NotNull() {
        val mainList = listOf<ShortAnime>()
        `when`(dao.getShortAnimeList(1)).thenReturn(Single.just(mainList))
        val repoMainAnimeList = repository.getLocalMainAnimeList()
        assertNotNull(repoMainAnimeList)
    }

    @Test
    fun getLocalWatchedAnimeList_Verify() {
        val watchedList = listOf<ShortAnime>()
        `when`(dao.getShortAnimeList(2)).thenReturn(Single.just(watchedList))
        repository.getLocalWatchedAnimeList()
        verify(dao, atLeastOnce()).getShortAnimeList(2)
    }

    @Test
    fun getLocalWatchedAnimeList_NotNull() {
        val watchedList = listOf<ShortAnime>()
        `when`(dao.getShortAnimeList(2)).thenReturn(Single.just(watchedList))
        val repoWatchedAnimeList = repository.getLocalWatchedAnimeList()
        assertNotNull(repoWatchedAnimeList)
    }

    @Test
    fun getLocalNotWatchedAnimeList_Verify() {
        val notWatchedList = listOf<ShortAnime>()
        `when`(dao.getShortAnimeList(3)).thenReturn(Single.just(notWatchedList))
        repository.getLocalNotWatchedAnimeList()
        verify(dao, atLeastOnce()).getShortAnimeList(3)
    }

    @Test
    fun getLocalNotWatchedAnimeList_NotNull() {
        val notWatchedList = listOf<ShortAnime>()
        `when`(dao.getShortAnimeList(3)).thenReturn(Single.just(notWatchedList))
        val repoNotWatchedAnimeList = repository.getLocalNotWatchedAnimeList()
        assertNotNull(repoNotWatchedAnimeList)
    }

    @Test
    fun getLocalWantedAnimeList_Verify() {
        val wantedList = listOf<ShortAnime>()
        `when`(dao.getShortAnimeList(4)).thenReturn(Single.just(wantedList))
        repository.getLocalWantedAnimeList()
        verify(dao, atLeastOnce()).getShortAnimeList(4)
    }

    @Test
    fun getLocalWantedAnimeList_NotNull() {
        val wantedList = listOf<ShortAnime>()
        `when`(dao.getShortAnimeList(4)).thenReturn(Single.just(wantedList))
        val repoWantedAnimeList = repository.getLocalWantedAnimeList()
        assertNotNull(repoWantedAnimeList)
    }

    @Test
    fun getLocalUnwantedAnimeList_Verify() {
        val notWantedList = listOf<ShortAnime>()
        `when`(dao.getShortAnimeList(5)).thenReturn(Single.just(notWantedList))
        repository.getLocalUnwantedAnimeList()
        verify(dao, atLeastOnce()).getShortAnimeList(5)
    }

    @Test
    fun getLocalUnwantedAnimeList_NotNull() {
        val notWantedList = listOf<ShortAnime>()
        `when`(dao.getShortAnimeList(5)).thenReturn(Single.just(notWantedList))
        val repoNotWantedAnimeList = repository.getLocalUnwantedAnimeList()
        assertNotNull(repoNotWantedAnimeList)
    }

    @Test
    fun insertLocalEntity_Verify() {
        val entity = mock(Entity::class.java)
        doNothing().`when`(dao).insert(entity)
        repository.insertLocalEntity(entity)
        verify(dao, atLeastOnce()).insert(entity)
    }

    @Test
    fun insertLocalEntityList_Verify() {
        val entityList = listOf<Entity>()
        doNothing().`when`(dao).insertList(entityList)
        repository.insertLocalEntity(entityList)
        verify(dao, atLeastOnce()).insertList(entityList)
    }

    @Test
    fun updateLocalEntity_Verify() {
        doNothing().`when`(dao).update(1, 2)
        repository.updateLocalEntity(1, 2)
        verify(dao, atLeastOnce()).update(1, 2)
    }

    @Test
    fun updateFromNetwork_Verify() {
        val anime = AnimeResponse(
            2,
            "АККА",
            "ACCA",
            "ACCA",
            "Королевстство Дова",
            "The Dova Kingdom",
            "Image",
            "Data",
            "Драма",
            "Drama",
            "Ono Natsume",
            13,
            0,
            0,
            0,
            0,
            0,
            12
        )
        doNothing().`when`(dao).updateFromNetwork(
            "АККА",
            "ACCA",
            "ACCA",
            13,
            "Королевстство Дова",
            "The Dova Kingdom",
            12,
            "Image",
            0,
            "Data",
            "Драма",
            "Drama",
            "Ono Natsume",
            2
        )
        repository.updateFromNetwork(anime, 2)
        verify(dao, atLeastOnce()).updateFromNetwork(
            "АККА",
            "ACCA",
            "ACCA",
            13,
            "Королевстство Дова",
            "The Dova Kingdom",
            12,
            "Image",
            0,
            "Data",
            "Драма",
            "Drama",
            "Ono Natsume",
            2
        )
    }

    @Test
    fun getAnimeById_Verify() {
        val entity = Entity(
            1,
            "АККА",
            "ACCA",
            "ACCA",
            "Королевстство Дова",
            "The Dova Kingdom",
            "Image",
            "Data",
            "Драма",
            "Drama",
            "Ono Natsume",
            13,
            5,
            12,
            5,
            1
        )

        `when`(dao.getEntityById(1)).thenReturn(Single.just(entity))
        repository.getAnimeById(1)
        verify(dao, atLeastOnce()).getEntityById(1)
    }

    @Test
    fun getAnimeById_NotNull() {
        val entity = Entity(
            1,
            "АККА",
            "ACCA",
            "ACCA",
            "Королевстство Дова",
            "The Dova Kingdom",
            "Image",
            "Data",
            "Драма",
            "Drama",
            "Ono Natsume",
            13,
            5,
            12,
            5,
            1
        )

        `when`(dao.getEntityById(1)).thenReturn(Single.just(entity))
        val returnedAnime = repository.getAnimeById(1)
        assertNotNull(returnedAnime)
    }

    @Test
    fun updateRate_Verify() {
        doNothing().`when`(dao).updateRate(1, 5)
        repository.updateRate(1, 5)
        verify(dao, atLeastOnce()).updateRate(1, 5)
    }
}