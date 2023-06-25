package com.meeweel.anilist.presentation.mainFragment.dialogs

import android.R
import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.slider.RangeSlider
import com.meeweel.anilist.databinding.FilterLayoutBinding
import com.meeweel.anilist.presentation.mainFragment.adapter.AnimeListFilter


class FilterBottomDialog(
    context: Context,
    private val filter: AnimeListFilter,
    private val onSubmitFilter: () -> Unit,
) : BottomSheetDialog(context) {
    private val binding = FilterLayoutBinding.inflate(layoutInflater)

    init {
        with(binding) {
            setContentView(root)
            clearButton.setOnClickListener {
                filter.clearFilter()
                onSubmitFilter()
                cancel()
            }
            okButton.setOnClickListener { cancel() }

            yearsRangeSlider.addOnChangeListener(RangeSlider.OnChangeListener { _, _, _ ->
                filter.setYears(
                    yearsRangeSlider.values[0].toInt(), yearsRangeSlider.values[1].toInt()
                )
                onSubmitFilter()
            })
            genreSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val genres = AnimeListFilter.Genre.values()
                    filter.setGenre(genres[genreSpinner.selectedItemPosition])
                    onSubmitFilter()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
            sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val sorts = AnimeListFilter.Sort.values()
                    filter.setSort(sorts[sortSpinner.selectedItemPosition])
                    onSubmitFilter()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

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