package com.meeweel.anilist.view.fragments.baselistfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meeweel.anilist.R
import com.meeweel.anilist.model.App
import com.meeweel.anilist.model.AppState
import com.meeweel.anilist.model.data.ListFilterSet
import com.meeweel.anilist.model.data.ShortAnime
import com.meeweel.anilist.model.repository.LocalRepository
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    lateinit var repository: LocalRepository
    private val filter = ListFilterSet()

    init {
        App.appInstance.component.inject(baseViewModel = this)
    }

    protected val isRu: Boolean = App.ContextHolder.context.resources.getBoolean(R.bool.isRussian)
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private var actualData: List<ShortAnime> = listOf()

    fun getData(): LiveData<AppState> {
        return liveDataToObserve
    }

    fun getAnimeFromLocalSource() = getDataFromLocalSource()

    private fun postList(list: List<ShortAnime>) {
        Thread {
            liveDataToObserve.postValue(
                AppState.Success(
                    list
                )
            )
        }.start()
    }

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            actualData = getAnimeList()
            liveDataToObserve.postValue(
                AppState.Success(
                    actualData
                )
            )
        }.start()
    }

    fun setTitleText(text: String) {
        filter.setTitleText(text)
        postList(filter.filter(actualData))
    }

    fun setSort(sort: ListFilterSet.Sort) {
        filter.setSort(sort)
        postList(filter.filter(actualData))
    }

    fun setGenre(genre: ListFilterSet.Genre) {
        filter.setGenre(genre)
        postList(filter.filter(actualData))
    }

    fun setYears(yearFrom: Int, yearTo: Int) {
        filter.setYears(yearFrom, yearTo)
        postList(filter.filter(actualData))
    }

    fun clearFilter() {
        filter.clear()
        postList(filter.filter(actualData))
    }

    abstract fun getAnimeList(): List<ShortAnime>
}