package com.meeweel.anilist.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import com.github.terrakok.cicerone.NavigatorHolder
import com.google.android.gms.ads.MobileAds
import com.google.android.material.snackbar.Snackbar
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.ActivityMainBinding
import com.meeweel.anilist.data.retrofit.AnimeSynchronizer
import com.meeweel.anilist.app.App
import com.meeweel.anilist.ui.navigation.CustomNavigator
import com.meeweel.anilist.ui.navigation.CustomRouter
import com.meeweel.anilist.ui.fragments.mainfragment.MainScreen
//import com.meeweel.anilist.workmanager.SynchronizeWorker
//import com.meeweel.anilist.workmanager.SynchronizeWorker.Companion.outPutKey
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private val navigator = CustomNavigator(activity = this, R.id.container)
    private lateinit var binding: ActivityMainBinding

    // WorkManager
    var constraints: Constraints = Constraints.Builder() // Создаёт ограничение запуска
        .setRequiredNetworkType(NetworkType.CONNECTED) // Должно быть подключение сети
        .build()
//    private val syncRequest = OneTimeWorkRequest.Builder(SynchronizeWorker::class.java) // Создаём запрос
//        .setConstraints(constraints) // Указываем ограничения для запроса
//        .build()
//    private val workManager = WorkManager.getInstance(application) // Создаём менеджер запросов

    @Inject
    lateinit var syncer: AnimeSynchronizer

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var appRouter: CustomRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        App.appInstance.component.inject(this)
        MobileAds.initialize(this)
        appRouter.navigateTo(MainScreen())
        if (savedInstanceState == null) {
            // WorkManager run task
//            workManager.enqueue(syncRequest) // Отправляем запрос в менеджер запросов
//            workManager.getWorkInfoByIdLiveData(syncRequest.id) // Получаем статус запроса по айди запроса
//                .observe(this, Observer<WorkInfo>() {
//                when (it.state) {
//                    WorkInfo.State.RUNNING -> toast("Synchronizing...")
//                    WorkInfo.State.SUCCEEDED -> appRouter.navigateTo(MainScreen())
//                }
//                    toast(workManager.getWorkInfoByIdLiveData(syncRequest.id).value?.outputData?.getInt(outPutKey,0).toString()) // Вывод данных
//            })
        }
    }

    private fun toast(text: String) {
        val snackbar =
            Snackbar.make(binding.container, text, Snackbar.LENGTH_SHORT)
        snackbar.setAction("UNDO") {
            Toast.makeText(applicationContext, "Undo action", Toast.LENGTH_SHORT).show()
        }
        snackbar.show()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
    companion object {
        var time = System.currentTimeMillis() - 20000L
        const val adsDelay = 3000000L

        const val MAIN = 1
        const val WATCHED = 2
        const val NOT_WATCHED = 3
        const val WANTED = 4
        const val UNWANTED = 5
    }
}