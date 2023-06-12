package com.meeweel.anilist.presentation.detailsFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.NewDetailsFragmentBinding
import com.meeweel.anilist.domain.models.Anime
import com.meeweel.anilist.presentation.NewMainActivity

class DetailsFragment : Fragment() {
    private var animeId: Int? = null
    private var _binding: NewDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this)[DetailsViewModel::class.java]
    }

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
        animeId = requireArguments().getInt(NewMainActivity.ARG_ANIME_ID)
        animeId?.let { viewModel.getAnimeById(it) }
        viewModel.listToObserve.observe(viewLifecycleOwner) {
            when (it) {
                is AnimeState.Success -> {
                    val data = it.animeData
                    populateData(data)
                    turnLoading(false)
                }
                is AnimeState.Error -> TODO()
                AnimeState.Loading -> turnLoading(true)
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