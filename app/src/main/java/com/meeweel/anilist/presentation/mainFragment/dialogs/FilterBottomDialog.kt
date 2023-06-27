package com.meeweel.anilist.presentation.mainFragment.dialogs

import android.content.Context
import android.os.Bundle
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
    private val onSubmitFilter: (filter: AnimeListFilter) -> Unit,
) : BottomSheetDialog(context) {
    private val binding = FilterLayoutBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setDialogFilters()
        setListeners()
    }

    private fun setListeners() {
        with(binding) {
            clearButton.setOnClickListener {
                filter.clearFilter()
                onSubmitFilter(filter)
                cancel()
            }
            okButton.setOnClickListener { cancel() }

            yearsRangeSlider.addOnChangeListener(RangeSlider.OnChangeListener { _, _, _ ->
                filter.setYears(
                    yearsRangeSlider.values[0].toInt(), yearsRangeSlider.values[1].toInt()
                )
                onSubmitFilter(filter)
            })
            genreSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val genres = AnimeListFilter.Genre.values()
                    filter.setGenre(genres[genreSpinner.selectedItemPosition])
                    onSubmitFilter(filter)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
            sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val sorts = AnimeListFilter.Sort.values()
                    filter.setSort(sorts[sortSpinner.selectedItemPosition])
                    onSubmitFilter(filter)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    private fun setDialogFilters() {
        val genres = AnimeListFilter.Genre.values()
        val genresList = genres.map(AnimeListFilter.Genre::textName).toMutableList()
        val sorts = AnimeListFilter.Sort.values()
        val sortsList = sorts.map(AnimeListFilter.Sort::textName).toMutableList()
        binding.genreSpinner.adapter = ArrayAdapter(
            context, android.R.layout.simple_spinner_dropdown_item, genresList
        )
        binding.genreSpinner.setSelection(filter.getGenre().ordinal)
        binding.sortSpinner.adapter =
            ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, sortsList)
        binding.sortSpinner.setSelection(filter.getSort().ordinal)
        binding.yearsRangeSlider.values =
            listOf(filter.getYearFrom().toFloat(), filter.getYearTo().toFloat())
    }
}