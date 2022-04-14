package com.meeweel.anilist.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.NavigatorHolder
import com.google.android.gms.ads.MobileAds
import com.google.android.material.snackbar.Snackbar
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.ActivityMainBinding
import com.meeweel.anilist.model.App
import com.meeweel.anilist.navigation.CustomNavigator
import com.meeweel.anilist.navigation.CustomRouter
import com.meeweel.anilist.view.fragments.mainfragment.MainScreen
import com.meeweel.anilist.model.AnimeSynchronizer
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private val navigator = CustomNavigator(activity = this, R.id.container)
    private lateinit var binding: ActivityMainBinding

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
        if (savedInstanceState == null) {
            appRouter.navigateTo(MainScreen())
//            toast("Start synchronization")
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

        const val MAIN = 1
        const val WATCHED = 2
        const val NOT_WATCHED = 3
        const val WANTED = 4
        const val UNWANTED = 5
    }
}