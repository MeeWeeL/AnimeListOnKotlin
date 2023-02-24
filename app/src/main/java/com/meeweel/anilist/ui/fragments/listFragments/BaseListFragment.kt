package com.meeweel.anilist.ui.fragments.listFragments

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.meeweel.anilist.R
import com.meeweel.anilist.app.App
import com.meeweel.anilist.data.repository.Repository
import com.meeweel.anilist.databinding.FilterLayoutBinding
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
import javax.inject.Inject

abstract class BaseListFragment : Fragment() {

    @Inject
    lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appInstance.component.inject(this)
    }

    // ADS
    private var mInterstitialAd: InterstitialAd? = null
    private var TAG = "MainActivity"
    private var adRequest = AdRequest.Builder().build()

    abstract val loadingLayoutView: View
    abstract val viewModel: BaseViewModel
    abstract val adapter: BaseFragmentAdapter

    protected fun initObserver() {
        val observer = Observer<AppState> { a -> renderData(a) }
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
            loadingLayoutView.visibility = View.VISIBLE
            val animeData = data.animeData
            adapter.submitList(animeData) {
                if (data.isFiltered) scrollUp()
                Log.e("submitList", "END")
                loadingLayoutView.visibility = View.GONE
            }
        }
        is AppState.Loading -> {
            loadingLayoutView.visibility = View.VISIBLE
        }
        is AppState.Error -> {
            loadingLayoutView.visibility = View.GONE
        }
    }

    abstract fun scrollUp()

    protected fun refresh(start: NavPoint = NavPoint.MAIN, end: NavPoint = NavPoint.MAIN) {
        val newTime = System.currentTimeMillis()
        if (System.currentTimeMillis() - MainActivity.time > MainActivity.adsDelay) {
            MainActivity.time = newTime
            showAds()
        }
        val navigationId: Int? = when (start) {
            NavPoint.MAIN -> when (end) {
                NavPoint.WATCHED -> R.id.action_mainFragment_to_watchedFragment
                NavPoint.NOT_WATCHED -> R.id.action_mainFragment_to_notWatchedFragment
                NavPoint.WANTED -> R.id.action_mainFragment_to_wantedFragment
                NavPoint.UNWANTED -> R.id.action_mainFragment_to_unwantedFragment
                else -> null
            }
            NavPoint.WATCHED -> when (end) {
                NavPoint.MAIN -> R.id.action_watchedFragment_to_mainFragment
                NavPoint.NOT_WATCHED -> R.id.action_watchedFragment_to_notWatchedFragment
                NavPoint.WANTED -> R.id.action_watchedFragment_to_wantedFragment
                NavPoint.UNWANTED -> R.id.action_watchedFragment_to_unwantedFragment
                else -> null
            }
            NavPoint.NOT_WATCHED -> when (end) {
                NavPoint.MAIN -> R.id.action_notWatchedFragment_to_mainFragment
                NavPoint.WATCHED -> R.id.action_notWatchedFragment_to_watchedFragment
                NavPoint.WANTED -> R.id.action_notWatchedFragment_to_wantedFragment
                NavPoint.UNWANTED -> R.id.action_notWatchedFragment_to_unwantedFragment
                else -> null
            }
            NavPoint.WANTED -> when (end) {
                NavPoint.MAIN -> R.id.action_wantedFragment_to_mainFragment
                NavPoint.WATCHED -> R.id.action_wantedFragment_to_watchedFragment
                NavPoint.NOT_WATCHED -> R.id.action_wantedFragment_to_notWatchedFragment
                NavPoint.UNWANTED -> R.id.action_wantedFragment_to_unwantedFragment
                else -> null
            }
            NavPoint.UNWANTED -> when (end) {
                NavPoint.MAIN -> R.id.action_unwantedFragment_to_mainFragment
                NavPoint.WATCHED -> R.id.action_unwantedFragment_to_watchedFragment
                NavPoint.NOT_WATCHED -> R.id.action_unwantedFragment_to_notWatchedFragment
                NavPoint.WANTED -> R.id.action_unwantedFragment_to_wantedFragment
                else -> null
            }
        }
        navigationId?.let { findNavController().navigate(resId = navigationId) }
    }

    enum class NavPoint {
        MAIN, WATCHED, NOT_WATCHED, WANTED, UNWANTED
    }

    private fun showAds() {
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad was dismissed.")
            }

//            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
//                Log.d(TAG, "Ad failed to show.")
//            }

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
            override fun onQueryTextSubmit(query: String?) = false
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

    protected fun showPopupMenu(anime: ShortAnime, view: View) {
        val popupMenu = PopupMenu(requireContext(), view, Gravity.END)
        popupMenu.inflate(getMenuId())
        popupMenu.setForceShowIcon(true)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.to_main -> {
                    popupMenuClick(anime, MAIN)
                    return@setOnMenuItemClickListener true
                }
                R.id.to_watched -> {
                    popupMenuClick(anime, WATCHED)
                    return@setOnMenuItemClickListener true
                }
                R.id.to_not_watched -> {
                    popupMenuClick(anime, NOT_WATCHED)
                    return@setOnMenuItemClickListener true
                }
                R.id.to_wanted -> {
                    popupMenuClick(anime, WANTED)
                    return@setOnMenuItemClickListener true
                }
                R.id.to_unwanted -> {
                    popupMenuClick(anime, UNWANTED)
                    return@setOnMenuItemClickListener true
                }
                else -> return@setOnMenuItemClickListener false
            }
        }
        popupMenu.show()
    }

    private fun popupMenuClick(anime: ShortAnime, list: Int) {
        repository.updateEntityLocal(anime.id, list)
        viewModel.removeAnime(anime)
    }

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

    interface OnItemViewClickListener {
        fun onItemViewClick(anime: ShortAnime)
    }

    interface OnLongItemViewClickListener {
        fun onLongItemViewClick(anime: ShortAnime, view: View, position: Int)
    }

    interface OnItemRemove {
        fun removeItem(anime: ShortAnime)
    }

    abstract fun getMenuId(): Int

}