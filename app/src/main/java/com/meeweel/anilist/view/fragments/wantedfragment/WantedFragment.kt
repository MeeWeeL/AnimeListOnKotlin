package com.meeweel.anilist.view.fragments.wantedfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.WantedFragmentBinding
import com.meeweel.anilist.model.App
import com.meeweel.anilist.model.AppState
import com.meeweel.anilist.model.data.ShortAnime
import com.meeweel.anilist.view.fragments.baselistfragment.BaseListFragment

class WantedFragment : BaseListFragment() {

    private var _binding: WantedFragmentBinding? = null
    private val binding
        get() = _binding!!

    override lateinit var adapter: WantedFragmentAdapter
    override val viewModel: WantedViewModel by lazy {
        ViewModelProvider(this).get(WantedViewModel::class.java).apply { App.appInstance.component.inject(this) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WantedFragmentBinding.inflate(inflater, container, false)
        adapter = WantedFragmentAdapter(repository)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter.removeClickListeners()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ItemTouchHelper(WantedItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.wantedFragmentRecyclerView)
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
        binding.navBar.menu.findItem(R.id.wanted_fragment_nav).isChecked = true
        binding.navBar.setOnNavigationItemSelectedListener(navBarListener)

        binding.wantedFragmentRecyclerView.adapter = adapter

        initObserver()

        binding.inputEditText.addTextChangedListener {
            viewModel.findByWord(it.toString())
        }
    }

    override fun renderData(data: AppState) = when (data) {
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

    override fun getMenuId(): Int {
        return R.menu.wanted_popup_menu
    }

    companion object {
        fun newInstance() = WantedFragment()
    }
}