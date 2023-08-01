package com.meeweel.anilist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.Navigation
import com.meeweel.anilist.R
import com.meeweel.anilist.data.sinchonizer.AnimeSynchronizer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewMainActivity : AppCompatActivity(R.layout.new_activity_main) {

    @Inject
    lateinit var syncer: AnimeSynchronizer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        syncer.synchronize()
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.hostFragment).navigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        syncer.onDestroy()
    }

    companion object {
        const val ARG_ANIME_ID = "AnimeID"
    }
}