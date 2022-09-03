package com.meeweel.anilist.ui.fragments.listFragments.lists.mainFragment


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
import com.meeweel.anilist.databinding.MainFragmentBinding
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.ui.MainActivity
import com.meeweel.anilist.ui.fragments.listFragments.BaseListFragment


class MainFragment : BaseListFragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    override val loadingLayoutView: View get() = binding.loadingLayout
    override val adapter: MainFragmentAdapter get() = adapterState!!

    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private val navBarListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.main_fragment_nav -> refresh(NavPoint.MAIN, NavPoint.MAIN)
            R.id.watched_fragment_nav -> refresh(NavPoint.MAIN, NavPoint.WATCHED)
            R.id.not_watched_fragment_nav -> refresh(NavPoint.MAIN, NavPoint.NOT_WATCHED)
            R.id.wanted_fragment_nav -> refresh(NavPoint.MAIN, NavPoint.WANTED)
            R.id.unwanted_fragment_nav -> refresh(NavPoint.MAIN, NavPoint.UNWANTED)
        }
        true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        if (adapterState == null)
            adapterState = MainFragmentAdapter(repository)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        ItemTouchHelper(MainItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.mainFragmentRecyclerView)
        adapter.setOnItemViewClickListener(object : OnItemViewClickListener {
            override fun onItemViewClick(anime: ShortAnime) {
                findNavController().navigate(
                    R.id.action_mainFragment_to_detailsFragment, bundleOf(
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
        binding.navBar.menu.findItem(R.id.main_fragment_nav).isChecked = true
        binding.navBar.setOnNavigationItemSelectedListener(navBarListener)

        binding.mainFragmentRecyclerView.adapter = adapter

        initObserver()
        setAppBarListeners()

        binding.toolbar.setNavigationOnClickListener {
            showProfileDialog()
        }
    }

    override fun getMenuItem(id: Int): MenuItem = binding.toolbar.menu.findItem(id)
    override fun getMenuId() = R.menu.main_popup_menu

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter.removeClickListeners()
    }

    companion object {
        var adapterState: MainFragmentAdapter? = null
    }
}