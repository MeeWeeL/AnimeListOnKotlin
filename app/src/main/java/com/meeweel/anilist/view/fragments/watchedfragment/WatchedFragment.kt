package com.meeweel.anilist.view.fragments.watchedfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.WatchedFragmentBinding
import com.meeweel.anilist.model.AppState
import com.meeweel.anilist.model.data.Anime
import com.meeweel.anilist.view.DetailsFragment
import com.meeweel.anilist.viewmodel.MainViewModel

class WatchedFragment : Fragment() {

    companion object {
        fun newInstance() = WatchedFragment()
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private var _binding: WatchedFragmentBinding? = null
    private val binding
        get() = _binding!!
    private val adapter = WatchedFragmentAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WatchedFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter.removeOnItemViewClickListener()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter.setOnItemViewClickListener(object: OnItemViewClickListener {
            override fun onItemViewClick(anime: Anime) {
                activity?.supportFragmentManager?.apply {
                    beginTransaction()
                        .replace(R.id.container, DetailsFragment.newInstance(Bundle().apply {
                            putParcelable(DetailsFragment.BUNDLE_EXTRA, anime)
                        }))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }
        })

        binding.watchedFragmentRecyclerView.adapter = adapter

        val observer = Observer<AppState> { a ->
            renderData(a)
        }
        viewModel.getData().observe(viewLifecycleOwner, observer)
        viewModel.getWatchedAnimeFromLocalSource()
    }


    private fun renderData(data: AppState) = when(data) {
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
    interface OnItemViewClickListener {
        fun onItemViewClick(anime: Anime)
    }
}