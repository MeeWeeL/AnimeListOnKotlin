package com.meeweel.anilist.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.ActivityMainBinding
import com.meeweel.anilist.model.room.App
import com.meeweel.anilist.view.fragments.mainfragment.MainFragment
import com.meeweel.anilist.view.fragments.notwatched.NotWatchedFragment
import com.meeweel.anilist.view.fragments.unwantedfragment.UnwantedFragment
import com.meeweel.anilist.view.fragments.wantedfragment.WantedFragment
import com.meeweel.anilist.view.fragments.watchedfragment.WatchedFragment
import com.meeweel.anilist.viewmodel.AnimeSynchronizer
import com.meeweel.anilist.viewmodel.Changing.setContext


class MainActivity : AppCompatActivity() {

    private lateinit var syncer: AnimeSynchronizer
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContext(this)
        syncer = AnimeSynchronizer((application as App).animeApi, binding)
        if (savedInstanceState == null) {
            refresh()
            toast("Start synchronization")
            syncer.synchronize()
        }
        binding.navBar.background = null
        binding.navBar.menu.findItem(R.id.main_fragment_nav).isChecked = true
        binding.navBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.main_fragment_nav -> refresh(MainFragment())
                R.id.watched_fragment_nav -> refresh(WatchedFragment())
                R.id.not_watched_fragment_nav -> refresh(NotWatchedFragment())
                R.id.wanted_fragment_nav -> refresh(WantedFragment())
                R.id.unwanted_fragment_nav -> refresh(UnwantedFragment())
            }
            true
        }
    }

    private fun refresh(fragment: Fragment = MainFragment()) {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, fragment)
            .commitNow()
    }
    private fun toast(text: String) {
        val snackbar =
            Snackbar.make(binding.container, text, Snackbar.LENGTH_SHORT)
        snackbar.setAction("UNDO") {
            Toast.makeText(applicationContext, "Undo action", Toast.LENGTH_SHORT).show()
        }
        snackbar.show()
    }
}