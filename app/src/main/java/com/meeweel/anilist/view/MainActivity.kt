package com.meeweel.anilist.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.snackbar.Snackbar
import com.meeweel.anilist.R
import com.meeweel.anilist.api.AnimeApi
import com.meeweel.anilist.databinding.ActivityMainBinding
import com.meeweel.anilist.model.repository.LocalRepository
import com.meeweel.anilist.model.room.App
import com.meeweel.anilist.model.room.App.Companion.appRouter
import com.meeweel.anilist.model.room.App.Companion.navigatorHolder
import com.meeweel.anilist.navigation.CustomNavigator
import com.meeweel.anilist.view.fragments.mainfragment.MainScreen
import com.meeweel.anilist.viewmodel.AnimeSynchronizer
import com.meeweel.anilist.viewmodel.Changing.setContext
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    lateinit var syncer: AnimeSynchronizer
    private val navigator = CustomNavigator(activity = this, R.id.container)
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MobileAds.initialize(this)
        setContext(this)
        syncer = AnimeSynchronizer((application as App).animeApi, binding)
        if (savedInstanceState == null) {
            appRouter.navigateTo(MainScreen())
            toast("Start synchronization")
            syncer.synchronize()
        }
    }

//    private fun refresh(fragment: Fragment = MainFragment()) {
//        supportFragmentManager.beginTransaction()
//            .replace(binding.container.id, fragment)
//            .commitNow()
//    }

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
    }
}