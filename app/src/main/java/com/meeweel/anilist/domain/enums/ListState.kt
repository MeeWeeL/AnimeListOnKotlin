package com.meeweel.anilist.domain.enums

enum class ListState(val int: Int) {
    MAIN(1),
    WATCHED(2),
    NOT_WATCHED(3),
    WANTED(4),
    UNWANTED(5),
    RATING_CHECK(2)
}