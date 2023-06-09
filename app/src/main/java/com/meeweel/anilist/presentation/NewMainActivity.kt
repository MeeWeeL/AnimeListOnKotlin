package com.meeweel.anilist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.meeweel.anilist.R
import com.meeweel.anilist.app.App
import com.meeweel.anilist.data.repository.RepositoryImpl
import com.meeweel.anilist.data.retrofit.AnimeSynchronizer
import com.meeweel.anilist.databinding.NewActivityMainBinding
import javax.inject.Inject

class NewMainActivity : AppCompatActivity() {

    private val syncer = AnimeSynchronizer(RepositoryImpl())
    private lateinit var binding: NewActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NewActivityMainBinding.inflate(layoutInflater)
        syncer.synchronize()
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.hostFragment).navigateUp()
    }
}