package com.meeweel.anilist.view.fragments.mainfragment



// ADS
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.github.terrakok.cicerone.Screen
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.MainFragmentBinding
import com.meeweel.anilist.model.AppState
import com.meeweel.anilist.model.data.ShortAnime
import com.meeweel.anilist.model.room.App.Companion.appRouter
import com.meeweel.anilist.navigation.CustomRouter
import com.meeweel.anilist.view.MainActivity.Companion.adsDelay
import com.meeweel.anilist.view.MainActivity.Companion.time
import com.meeweel.anilist.view.fragments.notwatched.NotWatchedScreen
import com.meeweel.anilist.view.fragments.unwantedfragment.UnwantedScreen
import com.meeweel.anilist.view.fragments.wantedfragment.WantedScreen
import com.meeweel.anilist.view.fragments.watchedfragment.WatchedScreen

class MainFragment(private val router: CustomRouter = appRouter) : Fragment() {

    // ADS
    private var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "MainActivity"
    var adRequest = AdRequest.Builder().build()

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private var _binding: MainFragmentBinding? = null
    private val binding
        get() = _binding!!
    private val adapter = MainFragmentAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter.removeOnItemViewClickListener()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ItemTouchHelper(MainItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.mainFragmentRecyclerView)
        adapter.setOnItemViewClickListener(object : OnItemViewClickListener {
            override fun onItemViewClick(anime: ShortAnime) {
                router.openDeepLink(anime)
//                activity?.supportFragmentManager?.apply {
//                    beginTransaction()
//                        .replace(R.id.container, DetailsFragment.newInstance(Bundle().apply {
//                            putParcelable(DetailsFragment.BUNDLE_EXTRA, anime)
//                        }))
//                        .addToBackStack("")
//                        .commitAllowingStateLoss()
//                }
            }
        })


        // ADS
        InterstitialAd.load(requireContext(),"ca-app-pub-1316488884400350/3455182241", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError?.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })


        binding.navBar.background = null
        binding.navBar.menu.findItem(R.id.main_fragment_nav).isChecked = true
        binding.navBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.main_fragment_nav -> refresh(MainScreen())
                R.id.watched_fragment_nav -> refresh(WatchedScreen())
                R.id.not_watched_fragment_nav -> refresh(NotWatchedScreen())
                R.id.wanted_fragment_nav -> refresh(WantedScreen())
                R.id.unwanted_fragment_nav -> refresh(UnwantedScreen())
            }
            true
        }

        binding.mainFragmentRecyclerView.adapter = adapter

        val observer = Observer<AppState> { a ->
            renderData(a)
        }
        viewModel.getData().observe(viewLifecycleOwner, observer)
        viewModel.getAnimeFromLocalSource()
    }


    private fun renderData(data: AppState) = when (data) {
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

    private fun refresh(fragment: Screen = MainScreen()) {
        val newTime = System.currentTimeMillis()
        if (System.currentTimeMillis() - time > adsDelay) {
            time = newTime
            showAds()
        }
        appRouter.replaceScreen(fragment)
    }

    private fun showAds() {
        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
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

    interface OnItemViewClickListener {
        fun onItemViewClick(anime: ShortAnime)
    }

    companion object {
        fun newInstance(): Fragment = MainFragment()
    }
}