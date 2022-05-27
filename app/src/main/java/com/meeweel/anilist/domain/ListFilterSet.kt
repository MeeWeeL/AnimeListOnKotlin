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
                if (item.enGenre.replaceAfter(genre, "").replaceBefore(genre, "") == genre) alterList.add(item)
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
            Sort.NEW -> list.sortedByDescending { it.data }
            Sort.OLD -> list.sortedBy { it.data }
            Sort.ID -> list.sortedBy { it.id }
            Sort.RATING -> list.sortedByDescending { it.rating }
        }
    }

    fun setGenre(genre: Genre) {
        this.genre = when (genre) {
            Genre.ALL_GENRES -> DEFAULT_GENRE_TEXT
            Genre.ISEKAI -> "Isekai"
            Genre.HORROR -> "Horror"
            Genre.COMEDY -> "Comedy"
            Genre.FANTASY -> "Fantasy"
            Genre.DRAMA -> "Drama"
            Genre.ACTION -> "Action"
            Genre.ROMANTIC -> "Romantic"
            Genre.SPACE -> "Space"
            Genre.WESTERN -> "Western"
            Genre.MYSTERY -> "Mystery"
            Genre.SPORTS -> "Sports"
            Genre.PSYCHOLOGICAL_THRILLER -> "Psychological Thriller"
            Genre.CRIME -> "Crime"
            Genre.ADVENTURE -> "Adventure"
            Genre.DARK_FANTASY -> "Dark Fantasy"
            Genre.SCIENCE_FICTION -> "Science Fiction"
            Genre.MECHA -> "Mecha"
            Genre.ALTERNATE_HISTORY -> "Alternate History"
            Genre.STEAMPUNK -> "Steampunk"
            Genre.ROMANCE -> "Romance"
            Genre.MILITARY -> "Military"
            Genre.ECCHI -> "Ecchi"
            Genre.SCI_FI -> "Sci-Fi"
            Genre.SHOUNEN -> "Shounen"
            Genre.SHOUJO -> "Shoujo"
            Genre.DEMONS -> "Demons"
            Genre.SCHOOL -> "School"
            Genre.SAMURAI -> "Samurai"
            Genre.HAREM -> "Harem"
            Genre.KIDS -> "Kids"
            Genre.GAME -> "Game"
            Genre.SLICE_OF_LIFE -> "Slice of Life"
            Genre.SUPERNATURAL -> "Supernatural"
        }
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

    enum class Sort {
        RATING, ALPHABET, NEW, OLD, ID
    }

    enum class Genre {
        ALL_GENRES, ISEKAI, ECCHI, COMEDY, FANTASY, DRAMA, ACTION, ROMANTIC, SPACE, HORROR, WESTERN, GAME, MYSTERY, SPORTS,
        PSYCHOLOGICAL_THRILLER, CRIME, ADVENTURE, DARK_FANTASY, SCIENCE_FICTION, MECHA, ALTERNATE_HISTORY,
        STEAMPUNK, ROMANCE, MILITARY, SCI_FI, SHOUNEN, SHOUJO, DEMONS, SCHOOL, SAMURAI, HAREM, KIDS,
        SLICE_OF_LIFE, SUPERNATURAL
    }

    companion object {
        const val DEFAULT_YEAR_FROM = 1900
        const val DEFAULT_YEAR_TO = 2100
        const val DEFAULT_TITLE_TEXT = ""
        const val DEFAULT_GENRE_TEXT = "All"
        val DEFAULT_SORT = Sort.ALPHABET
    }
}