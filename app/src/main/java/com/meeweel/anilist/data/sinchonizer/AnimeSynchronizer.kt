package com.meeweel.anilist.data.sinchonizer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.meeweel.anilist.data.retrofit.AnimeResponse
import com.meeweel.anilist.data.room.toEntityList
import com.meeweel.anilist.domain.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import javax.inject.Inject

class AnimeSynchronizer @Inject constructor(private val repository: Repository) {
    private var appVersion = "" // Установленная версия приложения
    private var actualQuantity = 0 // Количество сериалов, имеющихся на сервере
    private var localQuantity = 0  // Количество сериалов, которые уже загружены в приложение
    private val response: MutableLiveData<Response> = MutableLiveData() // Состояние синхронизации
    val syncLiveData: LiveData<Response> get() = response // Переменная, на которую подписывается активити
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    fun synchronize() { // Получение количества сериалов на сервере
        coroutineScope.launch {
            try {
                response.postValue(Response.CONNECTED)
                actualQuantity = repository.getQuantityRemote().id
                localQuantity() // Если получилось узнать с сервера количество сериалов, пытаемся получить количество в БД
            } catch (e: Exception) {
                response.postValue(Response.NO_INTERNET)
            }
        }
    }

    private fun localQuantity() { // Получение количества аниме из БД
        Log.d("Sync3", "Sync")

        coroutineScope.launch {
            localQuantity = try {
                repository.getAnimeQuantityLocal()
            } catch (e: Exception) {
                0 // Если произошла ошибка, при запросе в бд, значит в БД ещё ничего не было загружено
            }
            newAnimeComparison()
        }
    }

    private fun newAnimeComparison() {
        if (actualQuantity > localQuantity) {
            coroutineScope.launch {
                try {
                    response.postValue(Response.NEW_ANIME)
                    val animeList =
                        repository.getAnimeListRemote(localQuantity) // Запрос на загрузку с сервера новых сериалов
                    // Для этого передаём количество сериалов в БД. Оно является ID последнего загруженного сериала
                    // Так сервер поймёт, какие сериалы следует передать
                    insert(animeList) // Если новые сериалы пришли, сохраняем их в БД
                } catch (e: Exception) {
                    response.postValue(Response.SERVER_ERROR)
                }
            }
        } else {
            response.postValue(Response.ACTUAL_DATA)
        }
    }

    private fun insert(list: List<AnimeResponse>) { // Сохранение скаченных сериалов в БД
        coroutineScope.launch {
            repository.insertEntityLocal(list.toEntityList())
            response.postValue(Response.ANIME_UPLOADED)
        }
    }

    fun checkVersion() {
        coroutineScope.launch {
            try {
                val actualVersion =
                    repository.getActualVersionRemote() // Получение с сервера номер актуальной версии приложения в Google play
                response.postValue(
                    // Если номер текущей версии отличается от актуальной, сообщаем это активити
                    if (appVersion.isNotEmpty() && appVersion != actualVersion && actualVersion != "0") Response.HAS_NEWER_VERSION
                    else Response.HAVE_ACTUAL_VERSION
                )
            } catch (e: Exception) {
                response.postValue(Response.SERVER_ERROR)
            }
        }
    }

    fun getCounter(): Int =
        actualQuantity - localQuantity // Разница между количеством сериалов в БД и на сервере

    fun onDestroy() {
        coroutineScope.coroutineContext.cancelChildren() // Остановка всех асинхронных запросов при уничтожении активити
    }

    enum class Response {
        NO_INTERNET, CONNECTED, NEW_ANIME, SERVER_ERROR, ACTUAL_DATA, ANIME_UPLOADED, HAS_NEWER_VERSION, HAVE_ACTUAL_VERSION
    }
}
