package com.meeweel.anilist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.ActivityMainBinding
import com.meeweel.anilist.view.fragments.mainfragment.MainFragment
import com.meeweel.anilist.view.fragments.notwatched.NotWatchedFragment
import com.meeweel.anilist.view.fragments.unwantedfragment.UnwantedFragment
import com.meeweel.anilist.view.fragments.wantedfragment.WantedFragment
import com.meeweel.anilist.view.fragments.watchedfragment.WatchedFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        savedInstanceState?.let {} ?: refresh()
        binding.navBar.menu.findItem(R.id.main_fragment_nav).setChecked(true)
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

}