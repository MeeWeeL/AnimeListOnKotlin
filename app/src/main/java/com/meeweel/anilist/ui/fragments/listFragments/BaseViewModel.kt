package com.meeweel.anilist.ui.fragments.listFragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meeweel.anilist.app.App
import com.meeweel.anilist.domain.repository.Repository
import com.meeweel.anilist.domain.AppState
import com.meeweel.anilist.domain.ListFilterSet
import com.meeweel.anilist.domain.models.ShortAnime
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository
    private val filter = ListFilterSet()

    init {
        App.appInstance.component.inject(baseViewModel = this)
    }

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private var actualData: MutableList<ShortAnime> = mutableListOf()


    fun getData(): LiveData<AppState> {
        return liveDataToObserve
    }

    val allTitles get() = repository.getAllAnimeLocal()

    fun getAnimeFromLocalSource() = getDataFromLocalSource()

    private fun postList(list: List<ShortAnime>, isFiltered: Boolean = false) {
        liveDataToObserve.postValue(AppState.Success(filter.filter(list), isFiltered))
    }

    private fun getDataFromLocalSource() {
        getAnimeList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { liveDataToObserve.value = AppState.Loading }
            .subscribe({ list ->
                actualData.clear()
                actualData.addAll(filter.filter(list))
                postList(actualData)
            }, { postList(actualData) })
    }

    fun setTitleText(text: String) {
        filter.setTitleText(text)
        postList(filter.filter(actualData), true)
    }

    fun setSort(sort: ListFilterSet.Sort) {
        filter.setSort(sort)
        postList(filter.filter(actualData), true)
    }

    fun setGenre(genre: ListFilterSet.Genre) {
        filter.setGenre(genre)
        postList(filter.filter(actualData), true)
    }

    fun setYears(yearFrom: Int, yearTo: Int) {
        filter.setYears(yearFrom, yearTo)
        postList(filter.filter(actualData), true)
    }

    fun clearFilter() {
        filter.clear()
        postList(filter.filter(actualData), true)
    }

    fun getGenre(): ListFilterSet.Genre = filter.getGenre()
    fun getYearFrom(): Int = filter.getYearFrom()
    fun getYearTo(): Int = filter.getYearTo()
    fun getSort(): ListFilterSet.Sort = filter.getSort()

    fun removeAnime(anime: ShortAnime) {
        actualData.remove(anime)
        postList(actualData)
    }

    abstract fun getAnimeList(): Single<List<ShortAnime>>

}