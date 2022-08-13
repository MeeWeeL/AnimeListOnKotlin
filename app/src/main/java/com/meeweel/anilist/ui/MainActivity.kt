package com.meeweel.anilist.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.ads.MobileAds
import com.meeweel.anilist.app.App
import com.meeweel.anilist.data.retrofit.AnimeSynchronizer
import com.meeweel.anilist.data.retrofit.AnimeSynchronizer.Companion.RESPONSE_CONNECTED
import com.meeweel.anilist.data.retrofit.AnimeSynchronizer.Companion.RESPONSE_NEW_ANIME
import com.meeweel.anilist.data.retrofit.AnimeSynchronizer.Companion.RESPONSE_NO_INTERNET
import com.meeweel.anilist.data.retrofit.AnimeSynchronizer.Companion.RESPONSE_SERVER_ERROR
import com.meeweel.anilist.databinding.ActivityMainBinding
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var syncer: AnimeSynchronizer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        App.appInstance.component.inject(this)
        MobileAds.initialize(this)
        if (savedInstanceState == null) {
            val syncObserver = Observer<Int> { a -> renderData(a) }
            syncer.syncLiveData.observe(this, syncObserver)
            syncer.synchronize()
        }
    }

    private fun renderData(responseState: Int) {
        if (responseState > 0) {
            "$responseState new anime uploaded".toast()
        } else {
            when (responseState) {
                0 -> "You have actual data".toast()
                RESPONSE_NO_INTERNET -> "No connection".toast()
                RESPONSE_CONNECTED -> "Synchronization".toast()
                RESPONSE_NEW_ANIME -> "Found new anime".toast()
                RESPONSE_SERVER_ERROR -> "Server error".toast()
            }
        }
    }

    private fun String.toast() = Toast.makeText(this@MainActivity, this, Toast.LENGTH_SHORT).show()

    companion object {
        var time = System.currentTimeMillis() - 20000L
        const val adsDelay = 3000000L

        const val MAIN = 1
        const val WATCHED = 2
        const val NOT_WATCHED = 3
        const val WANTED = 4
        const val UNWANTED = 5

        const val ARG_ANIME_ID = "Anime ID"
    }
}