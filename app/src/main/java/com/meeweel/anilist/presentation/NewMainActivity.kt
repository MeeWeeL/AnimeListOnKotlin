package com.meeweel.anilist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.room.Room
import com.meeweel.anilist.R
import com.meeweel.anilist.app.App
import com.meeweel.anilist.data.room.RepositoryConst
import com.meeweel.anilist.data.repository.RepositoryImpl
import com.meeweel.anilist.data.retrofit.AnimeSynchronizer
import com.meeweel.anilist.data.retrofit.RetrofitImpl
import com.meeweel.anilist.data.room.EntityDataBase
import com.meeweel.anilist.databinding.NewActivityMainBinding

class NewMainActivity : AppCompatActivity() {

    private val syncer = AnimeSynchronizer(
        RepositoryImpl(
            RetrofitImpl.getService(),
            Room.databaseBuilder(
                App.ContextHolder.context,
                EntityDataBase::class.java,
                RepositoryConst.DB_NAME
            ).allowMainThreadQueries().build().entityDao()
        )
    )
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

    companion object {
        const val ARG_ANIME_ID = "AnimeID"
    }

}