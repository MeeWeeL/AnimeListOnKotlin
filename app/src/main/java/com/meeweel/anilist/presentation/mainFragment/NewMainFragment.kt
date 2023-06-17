package com.meeweel.anilist.presentation.mainFragment

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.NewFragmentMainBinding
import com.meeweel.anilist.domain.enums.ListState
import com.meeweel.anilist.presentation.NewMainActivity
import com.meeweel.anilist.presentation.mainFragment.adapter.NewAnimeListAdapter
import com.meeweel.anilist.presentation.mainFragment.adapter.NewMainItemTouchHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewMainFragment : Fragment(R.layout.new_fragment_main) {
    private var _binding: NewFragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewMainViewModel by viewModels()
    private val adapter: NewAnimeListAdapter by lazy { createAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = NewFragmentMainBinding.bind(view)
        ItemTouchHelper(NewMainItemTouchHelper(adapter)).attachToRecyclerView(binding.mainFragmentRecyclerView)
        binding.mainFragmentRecyclerView.adapter = adapter
        viewModel.listToObserve.observe(viewLifecycleOwner) {
            when (it) {
                is AnimeListState.Success -> {
                    adapter.submitList(it.animeData) {
                        turnLoading(false)
                    }
                }

                is AnimeListState.Error -> TODO()
                AnimeListState.Loading -> turnLoading(true)
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

    private fun showPopupMenu(animeId: Int, view: View, listState: ListState) {
        val popupMenu = PopupMenu(requireContext(), view, Gravity.END)
        when (listState) {
            ListState.MAIN -> popupMenu.inflate(R.menu.main_popup_menu)
            ListState.WATCHED -> popupMenu.inflate(R.menu.watched_popup_menu)
            ListState.NOT_WATCHED -> popupMenu.inflate(R.menu.not_watched_popup_menu)
            ListState.WANTED -> popupMenu.inflate(R.menu.wanted_popup_menu)
            ListState.UNWANTED -> popupMenu.inflate(R.menu.unwanted_popup_menu)
        }
        popupMenu.setForceShowIcon(true)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.to_main -> viewModel.changeAnimeState(animeId, ListState.MAIN)
                R.id.to_watched -> viewModel.changeAnimeState(animeId, ListState.WATCHED)
                R.id.to_not_watched -> viewModel.changeAnimeState(animeId, ListState.NOT_WATCHED)
                R.id.to_wanted -> viewModel.changeAnimeState(animeId, ListState.WANTED)
                R.id.to_unwanted -> viewModel.changeAnimeState(animeId, ListState.UNWANTED)
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    private fun createAdapter(): NewAnimeListAdapter {
        return NewAnimeListAdapter(object : NewAnimeListAdapter.AdapterCallback {
            override fun onItemClick(id: Int) {
                navigateFragmentToDetails(id)
            }

            override fun onItemStateChange(id: Int, state: ListState) {
                viewModel.changeAnimeState(id, state)
            }

            override fun onLongItemClick(id: Int, view: View, listState: ListState) {
                showPopupMenu(id, view, listState)
            }
        })
    }

    private fun navigateFragmentToDetails(animeId: Int) {
        findNavController().navigate(
            R.id.action_newMainFragment_to_detailsFragment2,
            bundleOf(NewMainActivity.ARG_ANIME_ID to animeId)
        )
    }

    private fun turnLoading(isLoading: Boolean) {
        binding.loadingLayout.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}