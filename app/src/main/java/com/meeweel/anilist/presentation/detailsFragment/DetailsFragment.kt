package com.meeweel.anilist.presentation.detailsFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import android.view.ViewTreeObserver
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

                is AnimeState.Error -> Toast.makeText(
                    context,
                    getString(R.string.animestateerror),
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun populateData(animeData: Anime) {
        val isRussian = context?.resources?.getBoolean(R.bool.isRussian) ?: false
        with(binding) {
            Glide.with(this.animeImage.context)
                .load(animeData.image)
                .error(R.drawable.anig)
                .into(this.animeImage)
            originalTitle.text = animeData.originalTitle
            ruTitle.text = animeData.ruTitle
            enTitle.text = animeData.enTitle
            if (isRussian) ruTitle.visibility = View.VISIBLE
            description.text = animeData.description
            author.text = "${getText(R.string.author)}: ${animeData.author}"
            genre.text = "${getText(R.string.genre)}: ${animeData.genre}"
            releaseDate.text = "${getText(R.string.data)}: ${animeData.data}"
            var ratingText = "${getText(R.string.rating)}: ${animeData.rating}%"

            if (animeData.ratingCheck == 0 && animeData.list == ListState.WATCHED.int) {
                ScoreBottomDialogue(
                    requireContext()
                ) { rate: Int ->
                    viewModel.rateAnime(animeId, rate)
                }.show()
            }

            if (animeData.ratingCheck != 0) ratingText += "\n(${getText(R.string.my_rate)}: ${animeData.ratingCheck})"
            rating.text = ratingText
            seriesQuantity.text =
                "${getText(R.string.seriesQuantity)}: ${animeData.seriesQuantity}"
            ageRate.text = "${getText(R.string.age_rating)}: ${animeData.ageRating}+"

            description.viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    description.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    descriptionImage.layoutParams.width = description.width
                    descriptionImage.layoutParams.height = description.height
                    Glide.with(descriptionImage.context)
                        .load(animeData.image)
                        .error(R.drawable.anig)
                        .into(descriptionImage)
                }
            })
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
