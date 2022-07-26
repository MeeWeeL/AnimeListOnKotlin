package com.meeweel.anilist.ui.fragments.unwantedfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.UnwantedFragmentBinding
import com.meeweel.anilist.app.App
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.ui.fragments.baselistfragment.BaseListFragment

class UnwantedFragment : BaseListFragment() {

    private var _binding: UnwantedFragmentBinding? = null
    private val binding
        get() = _binding!!
    override val loadingLayoutView: View
        get() = binding.loadingLayout

    override lateinit var adapter: UnwantedFragmentAdapter
    override val viewModel: UnwantedViewModel by lazy {
        ViewModelProvider(this).get(UnwantedViewModel::class.java)
            .apply { App.appInstance.component.inject(this) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UnwantedFragmentBinding.inflate(inflater, container, false)
        adapter = UnwantedFragmentAdapter()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter.removeClickListeners()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ItemTouchHelper(UnwantedItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.unwantedFragmentRecyclerView)
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
        binding.navBar.menu.findItem(R.id.unwanted_fragment_nav).isChecked = true
        binding.navBar.setOnNavigationItemSelectedListener(navBarListener)

        binding.unwantedFragmentRecyclerView.adapter = adapter

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
        return R.menu.unwanted_popup_menu
    }

    companion object {
        fun newInstance() = UnwantedFragment()
    }
}