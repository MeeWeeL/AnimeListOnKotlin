package com.meeweel.anilist.presentation.detailsFragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.BottomsheetRateLayoutBinding
import kotlinx.coroutines.coroutineScope

class ScoreBottomDialogue(
    context: Context,
    private val rateAnime: (rate: Int) -> Unit,
) : BottomSheetDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = BottomsheetRateLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rateStar1.setOnClickListener {
            rateAnime(1)
            binding.rateStar1.setImageResource(R.drawable.ic_star1_selected)
            cancel()
        }
        binding.rateStar2.setOnClickListener {
            rateAnime(2)
            binding.rateStar2.setImageResource(R.drawable.ic_star2_selected)
        }
        binding.rateStar3.setOnClickListener {
            rateAnime(3)
            binding.rateStar3.setImageResource(R.drawable.ic_star3_selected)
        }
        binding.rateStar4.setOnClickListener {
            rateAnime(4)
            binding.rateStar4.setImageResource(R.drawable.ic_star4_selected)
        }
        binding.rateStar5.setOnClickListener {
            rateAnime(5)
            binding.rateStar5.setImageResource(R.drawable.ic_star5_selected)
        }

        binding.rateButton.setOnClickListener {
            cancel()
        }
    }

    override fun cancel() {
        super.cancel()
    }
}