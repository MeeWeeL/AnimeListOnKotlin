package com.meeweel.anilist.newPresentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.google.android.material.navigation.NavigationBarView
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

    fun turnLoading(isLoading: Boolean) {
        binding.loadingLayout.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    fun getNavBar(): NavigationBarView = binding.navBar
}