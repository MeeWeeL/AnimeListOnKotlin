package com.meeweel.anilist.domain

import com.meeweel.anilist.domain.models.ShortAnime

class ListFilterSet {

    private var titleText = DEFAULT_TITLE_TEXT
    private var genre = DEFAULT_GENRE_TEXT
    private var yearFrom = DEFAULT_YEAR_FROM
    private var yearTo = DEFAULT_YEAR_TO
    private var sort = DEFAULT_SORT

    fun filter(list: List<ShortAnime>) : List<ShortAnime> {
        var newList = mutableListOf<ShortAnime>()

        for (item in list) {
            if (item.data.toInt() in yearFrom..yearTo) newList.add(item)
        }
        if (genre != DEFAULT_GENRE_TEXT) {
            val alterList = mutableListOf<ShortAnime>()
            for (item in newList) {
                if (item.enGenre.replaceAfter(genre.textName, "").replaceBefore(genre.textName, "") == genre.textName) alterList.add(item)
            }
            newList = alterList
        }
        return findByWord(sort(newList, sort))
    }

    private fun findByWord(list: List<ShortAnime>) : List<ShortAnime> {
        return if (titleText == DEFAULT_TITLE_TEXT) {
            list
        } else {
            val newList = mutableListOf<ShortAnime>()
            for (item in list) {
                if (item.enTitle.lowercase().replaceAfter(titleText.lowercase(), "").replaceBefore(titleText.lowercase(), "") == titleText.lowercase()
                    || item.ruTitle.lowercase().replaceAfter(titleText.lowercase(), "").replaceBefore(titleText.lowercase(), "") == titleText.lowercase()) {
                    newList.add(item)
                }
            }
            newList
        }
    }

    private fun sort(list: MutableList<ShortAnime>, sort: Sort) : List<ShortAnime> {
        return when(sort) {
            Sort.ALPHABET -> list.sortedBy { it.enTitle }
            Sort.ALPHABET_REVERS -> list.sortedByDescending { it.enTitle }
            Sort.NEW -> list.sortedByDescending { it.data }
            Sort.OLD -> list.sortedBy { it.data }
            Sort.RATING -> list.sortedByDescending { it.rating }
        }
    }

    fun setGenre(genre: Genre) {
        this.genre = genre
    }

    fun setYears(yearFrom: Int, yearTo: Int) {
        this.yearFrom = yearFrom
        this.yearTo = yearTo
    }

    fun setTitleText(text: String) {
        titleText = text
    }

    fun clear() {
        titleText = DEFAULT_TITLE_TEXT
        genre = DEFAULT_GENRE_TEXT
        yearFrom = DEFAULT_YEAR_FROM
        yearTo = DEFAULT_YEAR_TO
        sort = DEFAULT_SORT
    }

    fun setSort(sort: Sort) {
        this.sort = sort
    }

    enum class Sort(val textName: String) {
        ALPHABET("Alphabet"), ALPHABET_REVERS("Alphabet reverse"), RATING("Rating"), NEW("New"), OLD("Old")
    }

    enum class Genre(val textName: String,val pos: Int) {
        ALL_GENRES("All genres", 0), ISEKAI("Isekai", 1), ECCHI("Ecchi", 2), COMEDY("Comedy", 3),
        FANTASY("Fantasy", 4), DRAMA("Drama", 5), ACTION("Action", 6), ROMANTIC("Romantic", 7), SPACE("Space", 8), HORROR("Horror", 9), WESTERN("Western", 10), GAME("Game", 11), MYSTERY("Mystery", 12), SPORTS("Sports", 13),
        PSYCHOLOGICAL_THRILLER("Psychological Thriller", 14), CRIME("Crime", 15), ADVENTURE("Adventure", 16), DARK_FANTASY("Dark Fantasy", 17), SCIENCE_FICTION("Science Fiction", 18), MECHA("Mecha", 19), ALTERNATE_HISTORY("Alternate History", 20),
        STEAMPUNK("Steampunk", 21), ROMANCE("Romance", 22), MILITARY("Military", 23), SCI_FI("Sci-Fi", 24), SHOUNEN("Shounen", 25), SHOUJO("Shoujo", 26), DEMONS("Demons", 27), SCHOOL("School", 28), SAMURAI("Samurai", 29), HAREM("Harem", 30), KIDS("Kids", 31),
        SLICE_OF_LIFE("Slice of Life", 32), SUPERNATURAL("Supernatural", 33)
    }

    fun getGenre() : Genre {
        return genre
    }

    fun getYearFrom() : Int {
        return yearFrom
    }

    fun getYearTo() : Int {
        return yearTo
    }

    fun getSort() : Sort {
        return sort
    }

    companion object {
        const val DEFAULT_YEAR_FROM = 1969
        const val DEFAULT_YEAR_TO = 2022
        const val DEFAULT_TITLE_TEXT = ""
        val DEFAULT_GENRE_TEXT = Genre.ALL_GENRES
        val DEFAULT_SORT = Sort.ALPHABET
    }
}