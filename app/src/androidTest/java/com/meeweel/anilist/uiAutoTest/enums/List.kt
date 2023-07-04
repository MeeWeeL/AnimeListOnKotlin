package com.meeweel.anilist.uiAutoTest.enums

import com.meeweel.anilist.EspressoUtils.isRu

enum class List(
    val listNameRU: String,
    val listNameEN: String,
    val titleRU: String,
    val titleEN: String,
    ) {
    MAIN(if (isRu) "База" else "Main", "Main", if (isRu) "Бек: Восточная ударная группа" else "Beck: Mongolian Chop Squad", "Beck: Mongolian Chop Squad"),
    WATCHED(if (isRu) "Смотрел(а)" else "Watched", "Watched", if (isRu) "Волчица и чёрный принц" else "WOLF GIRL AND BLACK PRINCE", "WOLF GIRL AND BLACK PRINCE"),
    NOT_WATCHED(if (isRu) "Не смотрел(а)" else "not_watched", "not_watched", if (isRu) "Вайолет Эвергарден" else "Violet Evergarden", "Violet Evergarden"),
    WANTED(if (isRu) "Хочу" else "Wanted", "Wanted", if (isRu) "Крутой учитель Онидзука" else "GTO: Great Teacher Onizuka", "GTO: Great Teacher Onizuka"),
    UNWANTED(if (isRu) "Не хочу" else "Unwanted", "Unwanted", if (isRu) "Ковбой Бибоп" else "Cowboy Bebop", "Cowboy Bebop")
}