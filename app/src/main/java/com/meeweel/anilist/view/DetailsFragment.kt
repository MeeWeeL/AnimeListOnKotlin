package com.meeweel.anilist.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.DetailsFragmentBinding
import com.meeweel.anilist.model.data.Anime
import com.meeweel.anilist.model.room.isRussian
import com.meeweel.anilist.viewmodel.ImageMaker

class DetailsFragment : Fragment() {
    private val imageMaker: ImageMaker = ImageMaker()
    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Anime>(BUNDLE_EXTRA)?.let { anime ->
            populateData(anime)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun populateData(animeData: Anime) {
        with(binding) {
            binding.detailsBarImage.setImageBitmap(imageMaker.getPictureFromDirectory(animeData.image))
            binding.detailsDescriptionImage.setImageBitmap(imageMaker.getPictureFromDirectory(animeData.image))
            animeImage.setImageBitmap(imageMaker.getPictureFromDirectory(animeData.image))
            originalTitle.text = animeData.originalTitle
            englishTitle.text = animeData.enTitle
            russianTitle.text = animeData.ruTitle
            russianTitle.visibility = if (isRussian) View.VISIBLE else View.GONE
            descriptionValue.text = animeData.description
            releaseAuthor.text = "${getText(R.string.author)}: ${animeData.author}"
            releaseGenre.text = "${getText(R.string.genre)}: ${animeData.genre}"
            releaseData.text = "${getText(R.string.data)}: ${animeData.data}"
            releaseAgeRate.text = "${getText(R.string.age_rating)}: ${animeData.ageRating} +"
            releaseRating.text = "${getText(R.string.rating)}: ${animeData.rating} %"
        }
    }

    companion object {
        const val BUNDLE_EXTRA = "anime"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


}