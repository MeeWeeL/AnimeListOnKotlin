package com.meeweel.anilist.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.android.gms.ads.MobileAds
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.meeweel.anilist.BuildConfig
import com.meeweel.anilist.R
import com.meeweel.anilist.app.App
import com.meeweel.anilist.data.retrofit.AnimeSynchronizer
import com.meeweel.anilist.data.retrofit.AnimeSynchronizer.Response.*
import com.meeweel.anilist.databinding.ActivityMainBinding
import javax.inject.Inject

const val KEY_SP = "sp"
const val KEY_CURRENT_THEME = "current_theme"

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var syncer: AnimeSynchronizer // Иньекция синхронизатора, созданного в даггера, в этот класс

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setInstance(savedInstanceState)
        super.onCreate(mainActivityInstance)
        setNightMode(getCurrentTheme())
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        App.appInstance.component.inject(this) // Иньекция зависимостей из даггера в текущий класс
        MobileAds.initialize(this)
        if (savedInstanceState == null) {
            val syncObserver = Observer<AnimeSynchronizer.Response> { a -> renderData(a) } // Создание наблюдателя
            syncer.appVersion = BuildConfig.VERSION_NAME // Установка текущей версии приложения в синхронизаторе
            syncer.syncLiveData.observe(this, syncObserver) // Подписка на обновления статуса синхронизации
            syncer.synchronize() // Запуск синхронизации с сервером
        }
    }
    
    private fun renderData(responseState: AnimeSynchronizer.Response) {
        when (responseState) { // Действия в зависимости от состояния синхронизации
            ANIME_UPLOADED -> {
                "${syncer.getCounter()} new anime uploaded".toast()
                findNavController(R.id.nav_host_fragment).navigate(R.id.action_mainFragment_self) // Перезагрузка главного фрагмента, чтобы список обновился
                syncer.checkVersion() // Проверка на наличие обновлений
            }
            ACTUAL_DATA -> {
                "You have actual data".toast()
                syncer.checkVersion()
            }
            NO_INTERNET -> "No connection".toast()
            CONNECTED -> "Synchronization".toast()
            NEW_ANIME -> "Found new anime".toast()
            SERVER_ERROR -> "Server error".toast()
            HAS_NEWER_VERSION -> showUpdateDialog() // Вызов диалога с предложением перейти в Google play для обновления приложения
            HAVE_ACTUAL_VERSION -> {}
        }
    }

    private fun showUpdateDialog() {
        MaterialAlertDialogBuilder(this, R.style.updateDialog)
            .setTitle(R.string.newVersionAvailable)
            .setMessage(R.string.wannaUpdate)
            .setPositiveButton(R.string.yes) { d, _ -> // Кнопка справа и действия при нажатии на неё
                startActivity(
                    Intent( // Переход в Google play на страницу приложения
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=com.meeweel.anilist")
                    )
                )
                d.dismiss()
            }
            .setNeutralButton(R.string.later) { d, _ -> d.dismiss() } // Кнопка слева и действия при нажании на неё
            .show()
    }

    private fun String.toast() = Toast.makeText(this@MainActivity, this, Toast.LENGTH_SHORT).show() // Для удобного вызова тоста от любой стринги

    override fun onDestroy() {
        super.onDestroy()
        syncer.onDestroy() // При уничтожении активити, уничтожить все асинхронные потоки в синхронизаторе
    }

    fun setNightMode(isNightModeOn: Boolean) {
        val shardPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        val editor = shardPreferences.edit()
        editor.putBoolean(KEY_CURRENT_THEME, isNightModeOn)
        editor.apply()
        if (isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    fun getCurrentTheme(): Boolean {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        return sharedPreferences.getBoolean(KEY_CURRENT_THEME, false)
    }

    companion object {
        var mainActivityInstance: Bundle? = null
        fun setInstance(bundle: Bundle?) {
            if (bundle != null && mainActivityInstance != null)
            mainActivityInstance = bundle
        }
        var time = System.currentTimeMillis() - 20000L
        const val adsDelay = 300000L // Время перерыва между показами рекламы

        const val MAIN = 1
        const val WATCHED = 2
        const val NOT_WATCHED = 3
        const val WANTED = 4
        const val UNWANTED = 5

        const val ARG_ANIME_ID = "Anime ID"
    }
}