package com.meeweel.anilist.ui.fragments.mainfragment


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.MainFragmentBinding
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.ui.fragments.baselistfragment.BaseListFragment


class MainFragment : BaseListFragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding
        get() = _binding!!
    override val loadingLayoutView: View
        get() = binding.loadingLayout

    override lateinit var adapter: MainFragmentAdapter
    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        adapter = MainFragmentAdapter(repository)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter.removeClickListeners()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        ItemTouchHelper(MainItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.mainFragmentRecyclerView)
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
        binding.navBar.menu.findItem(R.id.main_fragment_nav).isChecked = true
        binding.navBar.setOnNavigationItemSelectedListener(navBarListener)

        binding.mainFragmentRecyclerView.adapter = adapter

        initObserver()
        setAppBarListeners()
    }

    override fun getMenuItem(id: Int): MenuItem {
        return binding.toolbar.menu.findItem(id)
    }

    override fun getMenuId(): Int {
        return R.menu.main_popup_menu
    }

    companion object {
        fun newInstance(): Fragment = MainFragment()
    }
}