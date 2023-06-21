package com.meeweel.anilist.presentation.mainFragment.dialogs

import android.R
import android.content.Context
import android.widget.ArrayAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.meeweel.anilist.databinding.FilterLayoutBinding
import com.meeweel.anilist.presentation.mainFragment.adapter.AnimeListFilter

class FilterBottomDialog(
    context: Context,
    private val filter: AnimeListFilter,
    private val onSubmitFilter: () -> Unit,
) : BottomSheetDialog(context) {
    private val binding = FilterLayoutBinding.inflate(layoutInflater)

    init {
        setContentView(binding.root)
        binding.clearButton.setOnClickListener {
            filter.clearFilter()
            onSubmitFilter()
            cancel()
        }
        binding.okButton.setOnClickListener {
            val sorts = AnimeListFilter.Sort.values()
            val genres = AnimeListFilter.Genre.values()
            filter.setSort(sorts[binding.sortSpinner.selectedItemPosition])
            filter.setGenre(genres[binding.genreSpinner.selectedItemPosition])
            filter.setYears(
                binding.yearsRangeSlider.values[0].toInt(),
                binding.yearsRangeSlider.values[1].toInt()
            )
            onSubmitFilter()
            cancel()
        }

    }


    fun setDialogFilters() {
        val genres = AnimeListFilter.Genre.values()
        val genresList = genres.map(AnimeListFilter.Genre::textName).toMutableList()
        val sorts = AnimeListFilter.Sort.values()
        val sortsList = sorts.map(AnimeListFilter.Sort::textName).toMutableList()
        binding.genreSpinner.adapter = ArrayAdapter(
            context, R.layout.simple_spinner_dropdown_item, genresList
        )
        binding.genreSpinner.setSelection(filter.getGenre().ordinal)
        binding.sortSpinner.adapter =
            ArrayAdapter(context, R.layout.simple_spinner_dropdown_item, sortsList)
        binding.sortSpinner.setSelection(filter.getSort().ordinal)
        binding.yearsRangeSlider.values =
            listOf(filter.getYearFrom().toFloat(), filter.getYearTo().toFloat())
    }

}