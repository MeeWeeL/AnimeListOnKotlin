package com.meeweel.anilist.view.fragments.notwatched

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.NotWatchedFragmentBinding
import com.meeweel.anilist.model.AppState
import com.meeweel.anilist.model.data.ShortAnime
import com.meeweel.anilist.view.fragments.baselistfragment.BaseListFragment

class NotWatchedFragment : BaseListFragment() {

    private var _binding: NotWatchedFragmentBinding? = null
    private val binding
        get() = _binding!!

    override val adapter = NotWatchedFragmentAdapter()
    override val viewModel: NotWatchedViewModel by lazy {
        ViewModelProvider(this).get(NotWatchedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NotWatchedFragmentBinding.inflate(inflater, container, false)
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
        return R.menu.not_watched_popup_menu
    }

    companion object {
        fun newInstance() = NotWatchedFragment()
    }
}