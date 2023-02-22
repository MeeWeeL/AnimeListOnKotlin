package com.meeweel.anilist.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.google.android.gms.ads.MobileAds
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.meeweel.anilist.BuildConfig
import com.meeweel.anilist.R
import com.meeweel.anilist.app.App
import com.meeweel.anilist.data.retrofit.AnimeSynchronizer
import com.meeweel.anilist.data.retrofit.AnimeSynchronizer.Response.*
import com.meeweel.anilist.databinding.ActivityMainBinding
import com.meeweel.anilist.utils.toast
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var syncer: AnimeSynchronizer // Иньекция синхронизатора, созданного в даггера, в этот класс

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setNightMode(getCurrentTheme())
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Иньекция зависимостей из даггера в текущий класс
        App.appInstance.component.inject(this)
        MobileAds.initialize(this)
    }

    override fun onResume() {
        super.onResume()
        if (!isSynchronized) {

            // Создание наблюдателя
            val syncObserver = Observer<AnimeSynchronizer.Response> { a -> renderSyncStatus(a) }

            // Установка текущей версии приложения в синхронизаторе
            syncer.appVersion = BuildConfig.VERSION_NAME

            // Подписка на обновления статуса синхронизации
            syncer.syncLiveData.observe(this, syncObserver)

            // Запуск синхронизации с сервером
            binding.loadingLayout.visibility = View.VISIBLE
            syncer.synchronize()
        }
    }

    private fun renderSyncStatus(responseState: AnimeSynchronizer.Response) {
        when (responseState) { // Действия в зависимости от состояния синхронизации
            ANIME_UPLOADED -> {
                binding.loadingLayout.visibility = View.GONE
                "${syncer.getCounter()} ${getString(R.string.new_anime_uploaded)}".toast()
                Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.action_mainFragment_self)
                syncer.checkVersion() // Проверка на наличие обновлений
            }
            ACTUAL_DATA -> {
                binding.loadingLayout.visibility = View.GONE
                getString(R.string.actual_data).toast()
                syncer.checkVersion()
            }
            NO_INTERNET -> getString(R.string.no_connection).toast()
            CONNECTED -> getString(R.string.synchronization).toast()
            NEW_ANIME -> getString(R.string.found_new_anime).toast()
            SERVER_ERROR -> getString(R.string.server_error).toast()
            HAS_NEWER_VERSION -> {
                binding.loadingLayout.visibility = View.GONE
                showUpdateDialog() // Вызов диалога с предложением перейти в Google play для обновления приложения
                isSynchronized = true
            }
            HAVE_ACTUAL_VERSION -> {
                binding.loadingLayout.visibility = View.GONE
                isSynchronized = true
            }
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

    private fun String.toast() = this.toast(this@MainActivity)

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

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        syncer.onDestroy() // При уничтожении активити, уничтожить все асинхронные потоки в синхронизаторе
    }

    companion object {

        const val KEY_SP = "sp"
        const val KEY_CURRENT_THEME = "current_theme"

        var isSynchronized = false
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