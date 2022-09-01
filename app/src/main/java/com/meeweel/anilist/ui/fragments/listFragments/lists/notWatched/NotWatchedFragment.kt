package com.meeweel.anilist.ui.fragments.listFragments.lists.notWatched

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.meeweel.anilist.R
import com.meeweel.anilist.app.App
import com.meeweel.anilist.databinding.NotWatchedFragmentBinding
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.ui.MainActivity
import com.meeweel.anilist.ui.fragments.listFragments.BaseListFragment

class NotWatchedFragment : BaseListFragment() {

    private var _binding: NotWatchedFragmentBinding? = null
    private val binding
        get() = _binding!!
    override val loadingLayoutView: View
        get() = binding.loadingLayout

    override lateinit var adapter: NotWatchedFragmentAdapter
    override val viewModel: NotWatchedViewModel by lazy {
        ViewModelProvider(this).get(NotWatchedViewModel::class.java)
            .apply { App.appInstance.component.inject(this) }
    }

    private val navBarListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.main_fragment_nav -> refresh(NavPoint.NOT_WATCHED, NavPoint.MAIN)
            R.id.watched_fragment_nav -> refresh(NavPoint.NOT_WATCHED, NavPoint.WATCHED)
            R.id.not_watched_fragment_nav -> refresh(NavPoint.NOT_WATCHED, NavPoint.NOT_WATCHED)
            R.id.wanted_fragment_nav -> refresh(NavPoint.NOT_WATCHED, NavPoint.WANTED)
            R.id.unwanted_fragment_nav -> refresh(NavPoint.NOT_WATCHED, NavPoint.UNWANTED)
        }
        true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NotWatchedFragmentBinding.inflate(inflater, container, false)
        adapter = NotWatchedFragmentAdapter(repository)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter.removeClickListeners()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ItemTouchHelper(NotWatchedItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.notWatchedFragmentRecyclerView)
        adapter.setOnItemViewClickListener(object : OnItemViewClickListener {
            override fun onItemViewClick(anime: ShortAnime) {
                findNavController().navigate(
                    R.id.action_notWatchedFragment_to_detailsFragment, bundleOf(
                        MainActivity.ARG_ANIME_ID to anime.id
                    )
                )
            }
        })
        adapter.setOnLongItemViewClickListener(object : OnLongItemViewClickListener {
            override fun onLongItemViewClick(anime: ShortAnime, view: View, position: Int) {
                showPopupMenu(anime, view, position)
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
        binding.navBar.menu.findItem(R.id.not_watched_fragment_nav).isChecked = true
        binding.navBar.setOnNavigationItemSelectedListener(navBarListener)

        binding.notWatchedFragmentRecyclerView.adapter = adapter

        initObserver()
        setAppBarListeners()

        binding.toolbar.setNavigationOnClickListener {
            showProfileDialog()
        }
    }

    override fun getMenuItem(id: Int): MenuItem {
        return binding.toolbar.menu.findItem(id)
    }

    override fun getMenuId(): Int {
        return R.menu.not_watched_popup_menu
    }

    companion object {
        fun newInstance() = NotWatchedFragment()
    }

    override fun displayIsListEmpty() = binding.isListEmpty
}