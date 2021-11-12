package com.meeweel.anilist.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
import com.meeweel.anilist.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit var syncer: AnimeSynchronizer
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setContext(this)
        syncer = AnimeSynchronizer((application as App).animeApi)
        savedInstanceState?.let {} ?: refresh()
        binding.navBar.background = null
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

    override fun onResume() {
        super.onResume()
        Toast.makeText(this,"sync", Toast.LENGTH_SHORT).show()
        syncer.synchronize()
    }
    private fun refresh(fragment: Fragment = MainFragment()) {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, fragment)
            .commitNow()
    }
}