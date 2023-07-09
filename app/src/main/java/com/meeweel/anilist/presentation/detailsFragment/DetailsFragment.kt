package com.meeweel.anilist.presentation.detailsFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.NewDetailsFragmentBinding
import com.meeweel.anilist.domain.models.Anime
import com.meeweel.anilist.presentation.NewMainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.new_details_fragment) {
    private var animeId: Int? = null
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