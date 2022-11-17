package com.meeweel.anilist.ui.fragments.listFragments.lists.wantedFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.navigation.NavigationBarView
import com.meeweel.anilist.R
import com.meeweel.anilist.app.App
import com.meeweel.anilist.databinding.WantedFragmentBinding
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.ui.MainActivity
import com.meeweel.anilist.ui.fragments.listFragments.BaseListFragment
import com.meeweel.anilist.utils.DialogsUtils

class WantedFragment : BaseListFragment() {

    private var _binding: WantedFragmentBinding? = null
    private val binding get() = _binding!!
    override val loadingLayoutView: View get() = binding.loadingLayout
    override val adapter by lazy { WantedFragmentAdapter(repository) }

    override val viewModel: WantedViewModel by lazy {
        ViewModelProvider(this)[WantedViewModel::class.java]
            .apply { App.appInstance.component.inject(this) }
    }

    private val navBarListener = NavigationBarView.OnItemSelectedListener {
        when (it.itemId) {
            R.id.main_fragment_nav -> refresh(NavPoint.WANTED, NavPoint.MAIN)
            R.id.watched_fragment_nav -> refresh(NavPoint.WANTED, NavPoint.WATCHED)
            R.id.not_watched_fragment_nav -> refresh(NavPoint.WANTED, NavPoint.NOT_WATCHED)
            R.id.wanted_fragment_nav -> refresh(NavPoint.WANTED, NavPoint.WANTED)
            R.id.unwanted_fragment_nav -> refresh(NavPoint.WANTED, NavPoint.UNWANTED)
        }
        true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WantedFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ItemTouchHelper(WantedItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.wantedFragmentRecyclerView)
        adapter.setOnItemViewClickListener(object : OnItemViewClickListener {
            override fun onItemViewClick(anime: ShortAnime) {
                findNavController().navigate(
                    R.id.action_wantedFragment_to_detailsFragment, bundleOf(
                        MainActivity.ARG_ANIME_ID to anime.id
                    )
                )
            }
        })
        adapter.setOnLongItemViewClickListener(object : OnLongItemViewClickListener {
            override fun onLongItemViewClick(anime: ShortAnime, view: View, position: Int) {
                showPopupMenu(anime, view)
            }
        })
        adapter.setOnItemRemove(object : OnItemRemove {
            override fun removeItem(anime: ShortAnime) {
                viewModel.removeAnime(anime)
            }
        })

        // ADS
        initAds()

        binding.navBar.background = null
        binding.navBar.menu.findItem(R.id.wanted_fragment_nav).isChecked = true
        binding.navBar.setOnItemSelectedListener(navBarListener)

        binding.wantedFragmentRecyclerView.adapter = adapter

        initObserver()
        setAppBarListeners()

        binding.toolbar.setNavigationOnClickListener {
            DialogsUtils.showProfileDialog(requireActivity(), viewModel.allTitles)
        }
    }

    override fun scrollUp() {
        binding.wantedFragmentRecyclerView.scrollToPosition(0)
    }

    override fun getMenuItem(id: Int): MenuItem = binding.toolbar.menu.findItem(id)
    override fun getMenuId() = R.menu.wanted_popup_menu

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter.removeClickListeners()
    }
}