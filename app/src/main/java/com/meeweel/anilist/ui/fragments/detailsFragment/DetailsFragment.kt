package com.meeweel.anilist.ui.fragments.detailsFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.meeweel.anilist.R
import com.meeweel.anilist.app.App
import com.meeweel.anilist.data.interactors.Interactor
import com.meeweel.anilist.data.repository.LocalRepository
import com.meeweel.anilist.data.retrofit.AnimeApi
import com.meeweel.anilist.databinding.DetailsFragmentBinding
import com.meeweel.anilist.databinding.RateBottomSheetLayoutBinding
import com.meeweel.anilist.domain.models.Anime
import com.meeweel.anilist.ui.MainActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class DetailsFragment : Fragment() {

    @Inject
    lateinit var interactor: Interactor

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
            observeData(it)
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
                val drawer = BottomShareDrawer(interactor)
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

            var ratingText = "${getText(R.string.rating)}: ${animeData.rating}%"
            if (animeData.ratingCheck != 0) ratingText += "\n(${getText(R.string.my_rate)}: ${animeData.ratingCheck})"
            releaseRating.text = ratingText

            seriesQuantity?.text =
                "${getText(R.string.seriesQuantity)}: ${animeData.seriesQuantity}"
            releaseAgeRate.text = "${getText(R.string.age_rating)}: ${animeData.ageRating}+"

            showRateBottomDialog(animeData)
        }
    }

    private fun showRateBottomDialog(animeData: Anime) {
        val dialog = BottomSheetDialog(requireContext())
        val dialogBinding: RateBottomSheetLayoutBinding =
            RateBottomSheetLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        fun changeRateScore(score: Int) {
            makeRateScore(animeData.id, score)
            interactor.updateRate(animeData.id, score)
            dialog.dismiss()
        }

        if (animeData.ratingCheck == 0 && animeData.list == MainActivity.WATCHED) {
            dialogBinding.rateCard.apply {
                dialogBinding.cancelBtn.setOnClickListener {
                    dialog.dismiss()
                }
                dialogBinding.star1.setOnClickListener {
                    changeRateScore(1)
                }
                dialogBinding.star2.setOnClickListener {
                    changeRateScore(2)
                }
                dialogBinding.star3.setOnClickListener {
                    changeRateScore(3)
                }
                dialogBinding.star4.setOnClickListener {
                    changeRateScore(4)
                }
                dialogBinding.star5.setOnClickListener {
                    changeRateScore(5)
                }
            }
            dialog.show()
        }
    }

    @SuppressLint("CheckResult")
    private fun makeRateScore(id: Int, score: Int) {
        interactor.rateScore(score, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                toast(getString(R.string.rated))
            }, {
                toast(getString(R.string.no_connection))
            })
        upload(false)
    }

    @SuppressLint("CheckResult")
    private fun upload(isRate: Boolean) {
        animeId?.let { id ->
            interactor.getAnime(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    interactor.updateFromNetwork(it, id)
                    observeData(id)
                    if (isRate) toast(getString(R.string.updated))
                }, {
                    toast(getString(R.string.no_connection))
                })
        }
    }

    private fun observeData(animeId: Int) {
        interactor.getAnimeById(animeId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                populateData(it)
            }, {
                toast(getString(R.string.no_connection))
            })
    }

    private fun toast(text: String) {
        val snackBar = Snackbar.make(binding.detailsBar, text, Snackbar.LENGTH_SHORT)
        snackBar.setAction(getString(R.string.skip)) {
//            Toast.makeText(getContext(), "Ok...", Toast.LENGTH_SHORT).show()
        }
        snackBar.show()
    }
}