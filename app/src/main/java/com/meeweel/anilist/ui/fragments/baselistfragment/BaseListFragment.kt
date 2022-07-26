package com.meeweel.anilist.ui.fragments.baselistfragment

import android.annotation.TargetApi
import android.content.ClipData
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.ClipboardManager
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.terrakok.cicerone.Screen
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.meeweel.anilist.R
import com.meeweel.anilist.app.App
import com.meeweel.anilist.data.repository.LocalRepository
import com.meeweel.anilist.databinding.FilterLayoutBinding
import com.meeweel.anilist.databinding.ProfileLayoutBinding
import com.meeweel.anilist.domain.AppState
import com.meeweel.anilist.domain.ListFilterSet.Genre
import com.meeweel.anilist.domain.ListFilterSet.Sort
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.ui.MainActivity
import com.meeweel.anilist.ui.MainActivity.Companion.MAIN
import com.meeweel.anilist.ui.MainActivity.Companion.NOT_WATCHED
import com.meeweel.anilist.ui.MainActivity.Companion.UNWANTED
import com.meeweel.anilist.ui.MainActivity.Companion.WANTED
import com.meeweel.anilist.ui.MainActivity.Companion.WATCHED
import com.meeweel.anilist.ui.fragments.mainfragment.*
import com.meeweel.anilist.ui.fragments.notwatched.NotWatchedScreen
import com.meeweel.anilist.ui.fragments.unwantedfragment.UnwantedScreen
import com.meeweel.anilist.ui.fragments.wantedfragment.WantedScreen
import com.meeweel.anilist.ui.fragments.watchedfragment.WatchedScreen
import com.meeweel.anilist.ui.navigation.CustomRouter
import javax.inject.Inject

abstract class BaseListFragment : Fragment() {

    @Inject
    lateinit var router: CustomRouter

    @Inject
    lateinit var repository: LocalRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appInstance.component.inject(this)
    }

    private val isRu get() = requireActivity().resources.getBoolean(R.bool.isRussian)

    // ADS
    private var mInterstitialAd: InterstitialAd? = null
    private var TAG = "MainActivity"
    private var adRequest = AdRequest.Builder().build()

    internal val navBarListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.main_fragment_nav -> refresh(MainScreen())
            R.id.watched_fragment_nav -> refresh(WatchedScreen())
            R.id.not_watched_fragment_nav -> refresh(NotWatchedScreen())
            R.id.wanted_fragment_nav -> refresh(WantedScreen())
            R.id.unwanted_fragment_nav -> refresh(UnwantedScreen())
        }
        true
    }

    abstract val loadingLayoutView: View
    abstract val viewModel: BaseViewModel
    abstract val adapter: BaseFragmentAdapter

    protected fun initObserver() {
        val observer = Observer<AppState> { a ->
            renderData(a)
        }
        viewModel.getData().observe(viewLifecycleOwner, observer)
        viewModel.getAnimeFromLocalSource()
    }

    protected fun initAds() {
        InterstitialAd.load(
            requireContext(),
            "ca-app-pub-1316488884400350/3455182241",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError.message)
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                }
            })
    }

    private fun renderData(data: AppState) = when (data) {
        is AppState.Success -> {
            val animeData = data.animeData
            loadingLayoutView.visibility = View.GONE
            adapter.setAnime(animeData)
        }
        is AppState.Loading -> {
            loadingLayoutView.visibility = View.VISIBLE
        }
        is AppState.Error -> {
            loadingLayoutView.visibility = View.GONE

        }
    }

    private fun refresh(fragment: Screen = MainScreen()) {
        val newTime = System.currentTimeMillis()
        if (System.currentTimeMillis() - MainActivity.time > MainActivity.adsDelay) {
            MainActivity.time = newTime
            showAds()
        }
        router.replaceScreen(fragment)
    }

    private fun showAds() {
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad was dismissed.")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d(TAG, "Ad failed to show.")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "Ad showed fullscreen content.")
                mInterstitialAd = null
            }
        }
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(requireActivity())
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
        }
    }

    protected fun setAppBarListeners() {
        val searchView: SearchView = getMenuItem(R.id.search_app_bar).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.setTitleText(newText!!)
                return false
            }
        })
        getMenuItem(R.id.filter_app_bar).setOnMenuItemClickListener {
            showFilterDialog()
            return@setOnMenuItemClickListener true
        }
    }

    abstract fun getMenuItem(id: Int): MenuItem

    protected fun showPopupMenu(anime: ShortAnime, view: View, position: Int) {
        val popupMenu = PopupMenu(requireContext(), view, Gravity.END)
        popupMenu.inflate(getMenuId())
        popupMenu.setForceShowIcon(true)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.to_main -> {
                    popupMenuClick(anime, MAIN, position)
                    return@setOnMenuItemClickListener true
                }
                R.id.to_watched -> {
                    popupMenuClick(anime, WATCHED, position)
                    return@setOnMenuItemClickListener true
                }
                R.id.to_not_watched -> {
                    popupMenuClick(anime, NOT_WATCHED, position)
                    return@setOnMenuItemClickListener true
                }
                R.id.to_wanted -> {
                    popupMenuClick(anime, WANTED, position)
                    return@setOnMenuItemClickListener true
                }
                R.id.to_unwanted -> {
                    popupMenuClick(anime, UNWANTED, position)
                    return@setOnMenuItemClickListener true
                }
                else -> return@setOnMenuItemClickListener false
            }
        }
        popupMenu.show()
    }

    private fun popupMenuClick(anime: ShortAnime, list: Int, position: Int) {
        repository.updateLocalEntity(anime.id, list)
        adapter.notifyRemove(anime, position)
        TOAST_MESSAGE.toast()
    }

    //, R.style.FilterDialogStyle
    private fun showFilterDialog() {
        val dialog = BottomSheetDialog(requireContext())
        val filterBinding = FilterLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(filterBinding.root)
        val genres = Genre.values()
        val genresList = mutableListOf<String>()
        genresList.addAll(genres.map(Genre::textName))
        val sorts = Sort.values()
        val sortsList = mutableListOf<String>()
        sortsList.addAll(sorts.map(Sort::textName))
        filterBinding.genreSpinner.adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                genresList
            )
        filterBinding.genreSpinner.setSelection(viewModel.getGenre().ordinal)
        filterBinding.sortSpinner.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, sortsList)
        filterBinding.sortSpinner.setSelection(viewModel.getSort().ordinal)
        filterBinding.yearsRangeSlider.values =
            listOf(viewModel.getYearFrom().toFloat(), viewModel.getYearTo().toFloat())
        dialog.show()

        filterBinding.clearButton.setOnClickListener {
            viewModel.clearFilter()
            dialog.cancel()
        }
        filterBinding.okButton.setOnClickListener {
            viewModel.setGenre(genres[filterBinding.genreSpinner.selectedItemPosition])
            viewModel.setYears(
                filterBinding.yearsRangeSlider.values[0].toInt(),
                filterBinding.yearsRangeSlider.values[1].toInt()
            )
            viewModel.setSort(sorts[filterBinding.sortSpinner.selectedItemPosition])
            dialog.cancel()
        }
        filterBinding.genreSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?, selectedItemView: View?,
                    position: Int, id: Long
                ) {
                    viewModel.setGenre(genres[filterBinding.genreSpinner.selectedItemPosition])
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {}
            }
        filterBinding.sortSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?, selectedItemView: View?,
                    position: Int, id: Long
                ) {
                    viewModel.setSort(sorts[filterBinding.sortSpinner.selectedItemPosition])
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {}
            }
        filterBinding.yearsRangeSlider.addOnChangeListener { _, _, _ ->
            viewModel.setYears(
                filterBinding.yearsRangeSlider.values[0].toInt(),
                filterBinding.yearsRangeSlider.values[1].toInt()
            )
        }
    }

    internal fun showProfileDialog() {
        val dialog = BottomSheetDialog(requireContext())
        val profileBinding = ProfileLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(profileBinding.root)
        var main = 0
        var watched = 0
        var wanted = 0
        var notWatched = 0
        var unwanted = 0
        var shortList: List<ShortAnime> = listOf()

        profileBinding.mainCopy.setOnClickListener { copy(MAIN, shortList) }
        profileBinding.watchedCopy.setOnClickListener { copy(WATCHED, shortList) }
        profileBinding.notWatchedCopy.setOnClickListener { copy(NOT_WATCHED, shortList) }
        profileBinding.wantedCopy.setOnClickListener { copy(WANTED, shortList) }
        profileBinding.unwantedCopy.setOnClickListener { copy(UNWANTED, shortList) }

        val profileObserver = Observer<List<ShortAnime>> { list ->
            shortList = list
            list.forEach { item ->
                when (item.list) {
                    MAIN -> main++
                    WATCHED -> watched++
                    NOT_WATCHED -> notWatched++
                    WANTED -> wanted++
                    UNWANTED -> unwanted++
                }
            }
            profileBinding.mainCounter.text = main.toString()
            profileBinding.watchedCounter.text = watched.toString()
            profileBinding.notWatchedCounter.text = notWatched.toString()
            profileBinding.wantedCounter.text = wanted.toString()
            profileBinding.unwantedCounter.text = unwanted.toString()
        }
        viewModel.shortLiveData.observe(viewLifecycleOwner, profileObserver)
        viewModel.getAll()
        dialog.show()
    }

    private fun copy(listInt: Int, list: List<ShortAnime>) {
        val copyList = StringBuilder()
        var count = 0
        list.sortedBy { item -> if (isRu) item.ruTitle else item.enTitle }.forEach {
            if (it.list == listInt) copyList.append(
                "${++count}. ${if (isRu) it.ruTitle else it.enTitle} (${it.data})\n") }
        copyText(copyList.toString())
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private fun copyText(copiedText: String) {
        val sdk = Build.VERSION.SDK_INT
        if (sdk < Build.VERSION_CODES.HONEYCOMB) {
            val clipboard =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.text = copiedText
            COPIED.toast()
        } else {
            val clipboard =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = ClipData.newPlainText("TAG", copiedText)
            clipboard.setPrimaryClip(clip)
            COPIED.toast()
        }
    }

    private fun String.toast() {
        Toast.makeText(requireContext(), this, Toast.LENGTH_SHORT).show()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(anime: ShortAnime)
    }

    interface OnLongItemViewClickListener {
        fun onLongItemViewClick(anime: ShortAnime, view: View, position: Int)
    }

    abstract fun getMenuId(): Int

    companion object {
        private const val TOAST_MESSAGE = "Moved"
        private const val COPIED = "Copied"
    }
}