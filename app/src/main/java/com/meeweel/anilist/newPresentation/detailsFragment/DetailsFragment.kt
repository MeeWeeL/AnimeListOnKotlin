package com.meeweel.anilist.newPresentation.detailsFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.DetailsFragmentBinding
import com.meeweel.anilist.databinding.NewDetailsFragmentBinding
import com.meeweel.anilist.domain.AppAnime
import com.meeweel.anilist.domain.models.Anime
import com.meeweel.anilist.model.data.AnimeResponse
import com.meeweel.anilist.newPresentation.mainFragment.NewMainViewModel
import com.meeweel.anilist.ui.MainActivity

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
        animeId = requireArguments().getInt(MainActivity.ARG_ANIME_ID)
        animeId?.let { viewModel.getAnimeById(it) }
        viewModel.listToObserve.observe(viewLifecycleOwner) {
            when (it) {
                is AppAnime.Success -> {
                    val data = it.animeData
                    populateData(data)
                    turnLoading(false)
                }
                is AppAnime.Error -> TODO()
                AppAnime.Loading -> turnLoading(true)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun populateData(animeData: AnimeResponse) {
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
            descriptionValue.text = animeData.ruDescription
            releaseAuthor.text = "${getText(R.string.author)}: ${animeData.author}"
            releaseGenre.text = "${getText(R.string.genre)}: ${animeData.ruGenre}"
            releaseData.text = "${getText(R.string.data)}: ${animeData.data}"
            val rating = calculateRating(animeData.rating1,animeData.rating2,animeData.rating3,animeData.rating4,animeData.rating5 )
            val ratingText = "${getText(R.string.rating)}: ${rating}%"
            // if (animeData.ratingCheck != 0) ratingText += "\n(${getText(R.string.my_rate)}: ${animeData.ratingCheck})"
            releaseRating.text = ratingText
            seriesQuantity.text =
                "${getText(R.string.seriesQuantity)}: ${animeData.seriesQuantity}"
            releaseAgeRate.text = "${getText(R.string.age_rating)}: ${animeData.ageRating}+"
        }
    }

    private fun calculateRating(r1: Int, r2: Int, r3: Int, r4: Int, r5: Int): Int {
        return try {
            (r2 * 25 + r3 * 50 + r4 * 75 + r5 * 100) / (r1 + r2 + r3 + r4 + r5)
        } catch (e: Exception) {
            0
        }
    }

    private fun turnLoading(isLoading: Boolean) {
        binding.loadingLayout.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}