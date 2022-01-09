package com.meeweel.anilist.view.fragments.unwantedfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.github.terrakok.cicerone.Screen
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.UnwantedFragmentBinding
import com.meeweel.anilist.model.AppState
import com.meeweel.anilist.model.data.Anime
import com.meeweel.anilist.model.data.ShortAnime
import com.meeweel.anilist.model.room.App.Companion.appRouter
import com.meeweel.anilist.navigation.CustomRouter
import com.meeweel.anilist.view.fragments.mainfragment.MainItemTouchHelperCallback
import com.meeweel.anilist.view.fragments.mainfragment.MainScreen
import com.meeweel.anilist.view.fragments.notwatched.NotWatchedScreen
import com.meeweel.anilist.view.fragments.wantedfragment.WantedScreen
import com.meeweel.anilist.view.fragments.watchedfragment.WatchedScreen

class UnwantedFragment(private val router: CustomRouter = appRouter) : Fragment() {

    companion object {
        fun newInstance() = UnwantedFragment()
    }

    private val viewModel: UnwantedViewModel by lazy {
        ViewModelProvider(this).get(UnwantedViewModel::class.java)
    }
    private var _binding: UnwantedFragmentBinding? = null
    private val binding
        get() = _binding!!
    private val adapter = UnwantedFragmentAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UnwantedFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter.removeOnItemViewClickListener()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ItemTouchHelper(UnwantedItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.unwantedFragmentRecyclerView)
        adapter.setOnItemViewClickListener(object : OnItemViewClickListener {
            override fun onItemViewClick(anime: ShortAnime) {
                router.openDeepLink(anime)
//                activity?.supportFragmentManager?.apply {
//                    beginTransaction()
//                        .replace(R.id.container, DetailsFragment.newInstance(Bundle().apply {
//                            putParcelable(DetailsFragment.BUNDLE_EXTRA, anime)
//                        }))
//                        .addToBackStack("")
//                        .commitAllowingStateLoss()
//                }
            }
        })

        binding.navBar.background = null
        binding.navBar.menu.findItem(R.id.unwanted_fragment_nav).isChecked = true
        binding.navBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.main_fragment_nav -> refresh(MainScreen())
                R.id.watched_fragment_nav -> refresh(WatchedScreen())
                R.id.not_watched_fragment_nav -> refresh(NotWatchedScreen())
                R.id.wanted_fragment_nav -> refresh(WantedScreen())
                R.id.unwanted_fragment_nav -> refresh(UnwantedScreen())
            }
            true
        }

        binding.unwantedFragmentRecyclerView.adapter = adapter

        val observer = Observer<AppState> { a ->
            renderData(a)
        }
        viewModel.getData().observe(viewLifecycleOwner, observer)
        viewModel.getUnwantedAnimeFromLocalSource()
    }


    private fun renderData(data: AppState) = when (data) {
        is AppState.Success -> {
            val animeData = data.animeData
            binding.loadingLayout.visibility = View.GONE
            adapter.setAnime(animeData)
        }
        is AppState.Loading -> {
            binding.loadingLayout.visibility = View.VISIBLE
        }
        is AppState.Error -> {
            binding.loadingLayout.visibility = View.GONE

        }
    }

    private fun refresh(fragment: Screen = MainScreen()) {
        appRouter.replaceScreen(fragment)
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(anime: ShortAnime)
    }
}