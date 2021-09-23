package com.meeweel.anilist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.DetailsFragmentBinding
import com.meeweel.anilist.model.data.Anime

class DetailsFragment : Fragment() {

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
        arguments?.getParcelable<Anime>(BUNDLE_EXTRA)?.let {
                anime -> populateData(anime)
        }
    }

    private fun populateData(animeData: Anime) {
        with(binding) {
            animeImage.setImageResource(animeData.image)
            animeName.text = animeData.title
            descriptionValue.setText(animeData.description)
            releaseAuthor.text = animeData.author
            releaseGenre.text = animeData.genre
            releaseData.text = animeData.data
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