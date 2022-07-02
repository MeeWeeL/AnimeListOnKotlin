package com.meeweel.anilist

import com.meeweel.anilist.data.retrofit.AnimeSynchronizer
import com.meeweel.anilist.model.data.MaxIdResponse
import com.meeweel.anilist.data.repository.LocalRepository
import com.meeweel.anilist.data.retrofit.AnimeApi
import com.meeweel.anilist.data.rx.ScheduleProviderStub
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class TestRx {

    private lateinit var presenter: AnimeSynchronizer
    @Mock
    private lateinit var repository: LocalRepository
    @Mock
    private lateinit var animeApi: AnimeApi

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = AnimeSynchronizer(animeApi, repository, ScheduleProviderStub())
    }

    @Test //Проверим вызов метода searchGitHub() у нашего Репозитория
    fun searchGitHub_Test() {
        `when`(animeApi.getQuantity()).thenReturn(
            Single.just(
                MaxIdResponse(
                    1
                )
            )
        )
        presenter.synchronize()
        verify(repository, times(1)).getQuantity()
    }

    companion object {
        private const val SEARCH_QUERY = "some query"
        private const val ERROR_TEXT = "error"
    }

}