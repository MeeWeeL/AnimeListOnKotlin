package com.meeweel.anilist.presentation.detailsFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.NewDetailsFragmentBinding
import com.meeweel.anilist.domain.enums.ListState
import com.meeweel.anilist.domain.models.Anime
import com.meeweel.anilist.domain.useCases.RateAnimeUseCase
import com.meeweel.anilist.presentation.NewMainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment(

) : Fragment(R.layout.new_details_fragment) {

    private val animeId: Int by lazy { requireArguments().getInt(NewMainActivity.ARG_ANIME_ID) }

    private var _binding: NewDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = NewDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animeId.let { viewModel.getAnimeById(it) }
        viewModel.listToObserve.observe(viewLifecycleOwner) { animeState ->
            when (animeState) {
                is AnimeState.Success -> {
                    val data = animeState.animeData
                    populateData(data)
                    turnLoading(false)
                }

                is AnimeState.Loading -> turnLoading(true)

                is AnimeState.Error -> Toast.makeText(context, getString(R.string.animestateerror), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun populateData(animeData: Anime) {
        with(binding) {
            Glide.with(this.detailsDescriptionImage.context)
                .load(animeData.image)
                .error(R.drawable.anig)
                .into(this.detailsDescriptionImage)
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

            if (animeData.ratingCheck == ListState.RATING_CHECK.int && animeData.list == ListState.WATCHED.int) {
                ScoreBottomDialogue(
                    requireContext()
                ) { rate: Int ->
                    viewModel.rateAnime(animeId, rate)
                }.show()
            } else {
                ScoreBottomDialogue(
                    requireContext()
                ) { rate: Int ->
                    viewModel.rateAnime(animeId, rate)
                }.cancel()
            }

            if (animeData.ratingCheck != 0) ratingText += "\n(${getText(R.string.my_rate)}: ${animeData.ratingCheck})"
            releaseRating.text = ratingText
            seriesQuantity.text =
                "${getText(R.string.seriesQuantity)}: ${animeData.seriesQuantity}"
            releaseAgeRate.text = "${getText(R.string.age_rating)}: ${animeData.ageRating}+"
        }
    }

    private fun turnLoading(isLoading: Boolean) {
        binding.loadingLayout.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
