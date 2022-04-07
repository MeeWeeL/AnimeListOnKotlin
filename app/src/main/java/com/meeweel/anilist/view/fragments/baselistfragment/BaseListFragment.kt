package com.meeweel.anilist.view.fragments.baselistfragment

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
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
import com.meeweel.anilist.R
import com.meeweel.anilist.model.App
import com.meeweel.anilist.model.AppState
import com.meeweel.anilist.model.data.ShortAnime
import com.meeweel.anilist.model.repository.LocalRepository
import com.meeweel.anilist.navigation.CustomRouter
import com.meeweel.anilist.view.MainActivity
import com.meeweel.anilist.view.MainActivity.Companion.MAIN
import com.meeweel.anilist.view.MainActivity.Companion.NOT_WATCHED
import com.meeweel.anilist.view.MainActivity.Companion.UNWANTED
import com.meeweel.anilist.view.MainActivity.Companion.WANTED
import com.meeweel.anilist.view.MainActivity.Companion.WATCHED
import com.meeweel.anilist.view.fragments.mainfragment.*
import com.meeweel.anilist.view.fragments.notwatched.NotWatchedScreen
import com.meeweel.anilist.view.fragments.unwantedfragment.UnwantedScreen
import com.meeweel.anilist.view.fragments.wantedfragment.WantedScreen
import com.meeweel.anilist.view.fragments.watchedfragment.WatchedScreen
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

    // ADS
    private var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "MainActivity"
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
                    Log.d(TAG, adError?.message)
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
                viewModel.findByWord(newText!!)
                return false
            }
        })
        getMenuItem(R.id.sort_app_bar).setOnMenuItemClickListener {
            Toast.makeText(requireContext(), "Hi Zhiwen", Toast.LENGTH_SHORT).show()
            return@setOnMenuItemClickListener true
        }
        getMenuItem(R.id.filter_app_bar).setOnMenuItemClickListener {
            Toast.makeText(requireContext(), "filter", Toast.LENGTH_SHORT).show()
            return@setOnMenuItemClickListener true
        }
        getMenuItem(R.id.me_app_bar).setOnMenuItemClickListener {
            Toast.makeText(requireContext(), "Here will be profile page", Toast.LENGTH_SHORT).show()
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
        toast(TOAST_MESSAGE)
    }

    private fun toast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
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
    }
}