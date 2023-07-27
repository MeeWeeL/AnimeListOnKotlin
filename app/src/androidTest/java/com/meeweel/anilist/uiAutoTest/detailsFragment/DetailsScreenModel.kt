package com.meeweel.anilist.uiAutoTest.detailsFragment

import com.meeweel.anilist.EspressoUtils.findViewById
import com.meeweel.anilist.R

class DetailsScreenModel {
    val scrollView get() = findViewById(R.id.details_scroll_view)
    val originalTitle get() = findViewById(R.id.original_title)
    val animeImage get() = findViewById(R.id.anime_image)
    val releaseDate get() = findViewById(R.id.release_date)
    val author get() = findViewById(R.id.author)
    val rating get() = findViewById(R.id.rating)
    val ageRate get() = findViewById(R.id.age_rate)
    val seriesQuantity get() = findViewById(R.id.series_quantity)
    val genre get() = findViewById(R.id.genre)
    val enTitle get() = findViewById(R.id.enTitle)
    val ruTitle get() = findViewById(R.id.ruTitle)
    val descriptionImage get() = findViewById(R.id.description_image)
    val description get() = findViewById(R.id.description)
}