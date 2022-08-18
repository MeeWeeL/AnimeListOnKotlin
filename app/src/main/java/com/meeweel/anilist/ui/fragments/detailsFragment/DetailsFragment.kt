package com.meeweel.anilist.ui.fragments.detailsFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.meeweel.anilist.R
import com.meeweel.anilist.app.App
import com.meeweel.anilist.data.repository.LocalRepository
import com.meeweel.anilist.data.retrofit.AnimeApi
import com.meeweel.anilist.databinding.DetailsFragmentBinding
import com.meeweel.anilist.domain.models.Anime
import com.meeweel.anilist.ui.MainActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class DetailsFragment : Fragment() {

    @Inject
    lateinit var animeApi: AnimeApi

    @Inject
    lateinit var repository: LocalRepository

    private var animeId: Int? = null
    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        App.appInstance.component.inject(this)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animeId = requireArguments().getInt(MainActivity.ARG_ANIME_ID)
        animeId?.let {
            populateData(repository.getAnimeById(it))
        }
        binding.detailsScrollView.setOnTouchListener(object :
            OnSwipeTouchListener(requireContext()) {
            override fun onSwipeLeft() {

            }

            override fun onSwipeRight() {
                findNavController().popBackStack()
            }
        })
        binding.detailsToolbar.menu.findItem(R.id.upload).setOnMenuItemClickListener {
            upload(true)
            true
        }
        binding.detailsToolbar.menu.findItem(R.id.share_to).setOnMenuItemClickListener {
            animeId?.let {
                val drawer = BottomShareDrawer(repository)
                val bundle = Bundle()
                bundle.putInt("aniId", it)
                drawer.arguments = bundle
                drawer.show(requireActivity().supportFragmentManager, "tag")
            }
            true
        }
    }

    @SuppressLint("SetTextI18n")
    private fun populateData(animeData: Anime) {
        with(binding) {

            Glide.with(this.detailsDescriptionImage.context)
                .load(animeData.image)
                .error(R.drawable.anig)
                .into(this.detailsDescriptionImage)
            Glide.with(this.detailsBarImage.context)
                .load(animeData.image)
                .error(R.drawable.anig)
                .into(this.detailsBarImage)
            Glide.with(this.animeImage.context)
                .load(animeData.image)
                .error(R.drawable.anig)
                .into(this.animeImage)

            originalTitle.text = animeData.originalTitle
            englishTitle.text = animeData.enTitle
            englishTitle.visibility = View.GONE
            russianTitle.text = animeData.ruTitle
            russianTitle.visibility = View.VISIBLE // if (isRussian) View.VISIBLE else View.GONE
            descriptionValue.text = animeData.description
            releaseAuthor.text = "${getText(R.string.author)}: ${animeData.author}"
            releaseGenre.text = "${getText(R.string.genre)}: ${animeData.genre}"
            releaseData.text = "${getText(R.string.data)}: ${animeData.data}"
            releaseAgeRate.text = "${getText(R.string.age_rating)}: ${animeData.ageRating}+"
            releaseRating.text = "${getText(R.string.rating)}: ${animeData.rating}%"

            if (animeData.ratingCheck == 0 && animeData.list == MainActivity.WATCHED) {
                binding.rateScore.apply {
                    rateCard.visibility = View.VISIBLE
                    cancelBtn.setOnClickListener {
                        rateCard.visibility = View.GONE
                    }
                    star1.setOnClickListener {
                        makeRateScore(animeData.id, 1)
                        repository.updateRate(animeData.id, 1)
                        rateCard.visibility = View.GONE
                    }
                    star2.setOnClickListener {
                        makeRateScore(animeData.id, 2)
                        repository.updateRate(animeData.id, 2)
                        rateCard.visibility = View.GONE
                    }
                    star3.setOnClickListener {
                        makeRateScore(animeData.id, 3)
                        repository.updateRate(animeData.id, 3)
                        rateCard.visibility = View.GONE
                    }
                    star4.setOnClickListener {
                        makeRateScore(animeData.id, 4)
                        repository.updateRate(animeData.id, 4)
                        rateCard.visibility = View.GONE
                    }
                    star5.setOnClickListener {
                        makeRateScore(animeData.id, 5)
                        repository.updateRate(animeData.id, 5)
                        rateCard.visibility = View.GONE
                    }
                }

            }
        }
    }

    @SuppressLint("CheckResult")
    private fun makeRateScore(id: Int, score: Int) {
        animeApi.reteScore(score, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                toast("Rated")
            }, {
                toast("No internet")
            })
        upload(false)
    }

    @SuppressLint("CheckResult")
    private fun upload(isRate: Boolean) {
        animeId?.let { id ->
            animeApi.getAnime(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    repository.updateFromNetwork(it, id)
                    populateData(repository.getAnimeById(id))
                    if (isRate) toast("Updated")
                }, {
                    toast("No internet")
                })
        }
    }

    private fun toast(text: String) {
        val snackBar = Snackbar.make(binding.detailsBar, text, Snackbar.LENGTH_SHORT)
        snackBar.setAction("SKIP") {
//            Toast.makeText(getContext(), "Ok...", Toast.LENGTH_SHORT).show()
        }
        snackBar.show()
    }
}