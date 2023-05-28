package com.meeweel.anilist.newPresentation.mainFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.NewFragmentMainBinding
import com.meeweel.anilist.domain.AppState
import com.meeweel.anilist.domain.enums.ListState
import com.meeweel.anilist.newPresentation.mainFragment.adapter.NewAnimeListAdapter
import com.meeweel.anilist.newPresentation.mainFragment.adapter.NewMainItemTouchHelper

class NewMainFragment : Fragment() {

    private var _binding: NewFragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewMainViewModel by lazy {
        ViewModelProvider(this)[NewMainViewModel::class.java]
    }
    private val adapter: NewAnimeListAdapter =
        NewAnimeListAdapter { id, state -> viewModel.changeAnimeState(id, state) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NewFragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ItemTouchHelper(NewMainItemTouchHelper(adapter)).attachToRecyclerView(binding.mainFragmentRecyclerView)
        binding.mainFragmentRecyclerView.adapter = adapter
        viewModel.listToObserve.observe(viewLifecycleOwner) {
            when (it) {
                is AppState.Success -> {
                    adapter.submitList(it.animeData) {
                        turnLoading(false)
                    }
                }
                is AppState.Error -> TODO()
                AppState.Loading -> turnLoading(true)
            }
        }
        binding.navBar.apply {
            selectedItemId = R.id.main_fragment_nav
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.unwanted_fragment_nav -> viewModel.changeListTo(ListState.UNWANTED)
                    R.id.not_watched_fragment_nav -> viewModel.changeListTo(ListState.NOT_WATCHED)
                    R.id.main_fragment_nav -> viewModel.changeListTo(ListState.MAIN)
                    R.id.wanted_fragment_nav -> viewModel.changeListTo(ListState.WANTED)
                    R.id.watched_fragment_nav -> viewModel.changeListTo(ListState.WATCHED)
                }
                return@setOnItemSelectedListener true
            }
        }
    }

    private fun turnLoading(isLoading: Boolean) {
        binding.loadingLayout.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}