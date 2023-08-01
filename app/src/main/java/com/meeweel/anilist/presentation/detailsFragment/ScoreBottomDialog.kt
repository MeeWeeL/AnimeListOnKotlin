package com.meeweel.anilist.presentation.detailsFragment

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.meeweel.anilist.R
import com.meeweel.anilist.databinding.RateLayoutBinding

class ScoreBottomDialog(
    context: Context,
    private val rateAnime: (rate: Int) -> Unit,
) : BottomSheetDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = RateLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            rateStar1.setOnClickListener {
                rateStar1.setImageResource(R.drawable.ic_star1_selected)
                rateAnime(1)
                dismiss()
            }
            rateStar2.setOnClickListener {
                rateStar2.setImageResource(R.drawable.ic_star2_selected)
                rateAnime(2)
                dismiss()
            }
            rateStar3.setOnClickListener {
                rateStar3.setImageResource(R.drawable.ic_star3_selected)
                rateAnime(3)
                dismiss()
            }
            rateStar4.setOnClickListener {
                rateStar4.setImageResource(R.drawable.ic_star4_selected)
                rateAnime(4)
                dismiss()
            }
            rateStar5.setOnClickListener {
                rateStar5.setImageResource(R.drawable.ic_star5_selected)
                rateAnime(5)
                dismiss()
            }

            rateButton.setOnClickListener {
                dismiss()
            }
        }
    }
}