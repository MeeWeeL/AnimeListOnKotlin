package com.meeweel.anilist.newUI

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.meeweel.anilist.R
import com.meeweel.anilist.app.App
import com.meeweel.anilist.data.retrofit.AnimeSynchronizer
import com.meeweel.anilist.databinding.NewActivityMainBinding
import javax.inject.Inject

class NewMainActivity : AppCompatActivity() {

    @Inject
    lateinit var syncer: AnimeSynchronizer
    private lateinit var binding: NewActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NewActivityMainBinding.inflate(layoutInflater)
        App.appInstance.component.inject(this)
        syncer.synchronize()
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.hostFragment).navigateUp()
    }


}