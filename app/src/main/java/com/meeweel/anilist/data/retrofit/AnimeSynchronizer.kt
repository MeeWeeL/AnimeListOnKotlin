package com.meeweel.anilist.data.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.meeweel.anilist.data.repository.LocalRepository
import com.meeweel.anilist.data.room.toEntityList
import com.meeweel.anilist.data.rx.SchedulerProvider
import com.meeweel.anilist.model.data.AnimeResponse
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

class AnimeSynchronizer @Inject constructor(
    private val aniApi: AnimeApi,
    private val repository: LocalRepository,
    private val schedulerProvider: SchedulerProvider
) {

    var appVersion = "" // Установленная версия приложения
    private var actualQuantity = 0 // Количество сериалов, имеющихся на сервере
    private var localQuantity = 0  // Количество сериалов, которые уже загружены в приложение

    // Создаём контейнер для хранения асинхронных запросов
    // Он нужен для прерывания асинхронных запросов, находящихся в нём, например при уничтожении активити
    private val compositeDisposable = CompositeDisposable()
    private val response: MutableLiveData<Response> = MutableLiveData() // Состояние синхронизации
    val syncLiveData get(): LiveData<Response> = response // Переменная, на которую подписывается активити

    fun synchronize() = aniApi.getQuantity()  // Получение количества сериалов на сервере
        .setSchedulers()
        .subscribe({
            response.postValue(Response.CONNECTED)
            actualQuantity = it.id
            localQuantity() // Если получилось узнать с сервера количество сериалов, пытаемся получить количество в БД
        }, {
            response.postValue(Response.NO_INTERNET)
        }).setDisposable()

    private fun localQuantity() = repository.getQuantity() // Получение количества аниме из БД
        .setSchedulers()
        .subscribe({
            localQuantity = it
            ifIf()
        }, {
            localQuantity =
                0 // Если произошла ошибка, при запросе в бд, значит в БД ещё ничего не было загружено
            ifIf()
        }).setDisposable()

    private fun ifIf() {
        if (actualQuantity > localQuantity) {
            aniApi.getAnimes(localQuantity) // Запрос на загрузку с сервера новых сериалов.
                // Для этого передаём количество сериалов в БД. Оно является ID последнего загруженного сериала
                // Так сервер поймёт, какие сериалы следует передать
                .setSchedulers()
                .subscribe({
                    response.postValue(Response.NEW_ANIME)
                    insert(it) // Если новые сериалы пришли, сохраняем их в БД
                }, {
                    response.postValue(Response.SERVER_ERROR)
                }).setDisposable()
        } else response.postValue(Response.ACTUAL_DATA)
    }

    private fun insert(list: List<AnimeResponse>) { // Сохранение скаченных сериалов в БД
        repository.insertLocalEntity(list.toEntityList())
        response.postValue(Response.ANIME_UPLOADED)
    }

    fun checkVersion() =
        aniApi.getActualVersion() // Получение с сервера номер актуальной версии приложения в Google play
            .setSchedulers()
            .subscribe({
                // Если номер текущей версии отличается от актуальной, сообщаем это активити
                response.postValue(if (appVersion.isNotEmpty() && appVersion != it) Response.HAS_NEWER_VERSION else Response.HAVE_ACTUAL_VERSION)
            }, {
                response.postValue(Response.SERVER_ERROR)
            }).setDisposable()

    fun getCounter(): Int =
        actualQuantity - localQuantity // Разница между количеством сериалов в БД и на сервере

    private fun <T : Any> Single<T>.setSchedulers(): Single<T> =
        this // Функция для сокращения повторяемого кода
            .subscribeOn(schedulerProvider.io()) // Производить асинхронную работу на легковесном потоке
            .observeOn(schedulerProvider.ui()) // Производить обработку асинхронно полученных данных в главном потоке

    private fun Disposable.setDisposable() =
        compositeDisposable.add(this) // Функция для помещения асинхронного запрова в композит

    fun onDestroy() =
        compositeDisposable.dispose() // Остановка всех асинхронных запросов при уничтожении активити

    enum class Response {
        NO_INTERNET,
        CONNECTED,
        NEW_ANIME,
        SERVER_ERROR,
        ACTUAL_DATA,
        ANIME_UPLOADED,
        HAS_NEWER_VERSION,
        HAVE_ACTUAL_VERSION
    }
}