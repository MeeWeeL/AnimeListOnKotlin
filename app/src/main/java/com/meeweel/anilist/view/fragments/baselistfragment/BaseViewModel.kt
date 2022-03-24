package com.meeweel.anilist.view.fragments.baselistfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.meeweel.anilist.R
import com.meeweel.anilist.model.App
import com.meeweel.anilist.model.AppState
import com.meeweel.anilist.model.data.ShortAnime
import com.meeweel.anilist.model.repository.LocalRepository
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    lateinit var repository: LocalRepository

    init {
        App.appInstance.component.inject(baseViewModel = this)
    }

    protected val isRu: Boolean = App.ContextHolder.context.resources.getBoolean(R.bool.isRussian)
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private var actualData: List<ShortAnime> = listOf()

    fun getData(): LiveData<AppState> {
        return liveDataToObserve
    }

    fun findByWord(text: String) {
        val list = listOf<ShortAnime>()
        if (text == "") {
            postList(actualData)
        } else {
            val newList = mutableListOf<ShortAnime>()
            for (item in actualData) {
                if (item.enTitle.lowercase().replaceAfter(text.lowercase(), "").replaceBefore(text.lowercase(), "") == text.lowercase()
                    || item.ruTitle.lowercase().replaceAfter(text.lowercase(), "").replaceBefore(text.lowercase(), "") == text.lowercase()) {
                    newList.add(item)
                }
            }
            postList(newList)
        }
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

    abstract fun getAnimeList(): List<ShortAnime>
}