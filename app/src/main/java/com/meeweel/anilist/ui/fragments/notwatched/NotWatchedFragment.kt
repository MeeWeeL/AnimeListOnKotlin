package com.meeweel.anilist.ui.fragments.notwatched

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.NotWatchedFragmentBinding
import com.meeweel.anilist.app.App
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.ui.fragments.baselistfragment.BaseListFragment

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
                router.openDeepLink(anime)
            }
        })
        adapter.setOnLongItemViewClickListener(object : OnLongItemViewClickListener {
            override fun onLongItemViewClick(anime: ShortAnime, view: View, position: Int) {
                showPopupMenu(anime, view, position)
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
}