package com.meeweel.anilist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.Navigation
import com.meeweel.anilist.R
import com.meeweel.anilist.data.sinchonizer.AnimeSynchronizer
import com.meeweel.anilist.databinding.NewActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewMainActivity : AppCompatActivity() {

    private lateinit var binding: NewActivityMainBinding
    @Inject lateinit var syncer: AnimeSynchronizer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NewActivityMainBinding.inflate(layoutInflater)
        syncer.synchronize()
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.hostFragment).navigateUp()
    }

    companion object {
        const val ARG_ANIME_ID = "AnimeID"
    }
}