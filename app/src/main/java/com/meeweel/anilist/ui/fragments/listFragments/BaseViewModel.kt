package com.meeweel.anilist.ui.fragments.listFragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meeweel.anilist.R
import com.meeweel.anilist.app.App
import com.meeweel.anilist.data.repository.LocalRepository
import com.meeweel.anilist.domain.AppState
import com.meeweel.anilist.domain.ListFilterSet
import com.meeweel.anilist.domain.models.ShortAnime
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    lateinit var repository: LocalRepository
    private val filter = ListFilterSet()

    init {
        App.appInstance.component.inject(baseViewModel = this)
    }

    private val isRu: Boolean = App.ContextHolder.context.resources.getBoolean(R.bool.isRussian)
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val shortLiveDataToObserve: MutableLiveData<List<ShortAnime>> = MutableLiveData()
    val shortLiveData: LiveData<List<ShortAnime>> get() = shortLiveDataToObserve
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
        getAnimeList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { liveDataToObserve.value = AppState.Loading }
            .subscribe({ list ->
                actualData = list.sortedBy { anime -> if (isRu) anime.ruTitle else anime.enTitle }
                liveDataToObserve.postValue(AppState.Success(actualData))
            }, { shortLiveDataToObserve.postValue(listOf()) })
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

    fun getGenre(): ListFilterSet.Genre = filter.getGenre()
    fun getYearFrom(): Int = filter.getYearFrom()
    fun getYearTo(): Int = filter.getYearTo()
    fun getSort(): ListFilterSet.Sort = filter.getSort()

    fun getAll() {
        repository.getAllAnime()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                actualData = it
                shortLiveDataToObserve.postValue(actualData)
            }, { shortLiveDataToObserve.postValue(listOf()) })
    }

    abstract fun getAnimeList(): Single<List<ShortAnime>>
}