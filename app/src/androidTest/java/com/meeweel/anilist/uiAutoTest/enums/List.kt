package com.meeweel.anilist.uiAutoTest.enums

import com.meeweel.anilist.data.room.isRussian

enum class List(
    val listNameRU: String,
    val listNameEN: String,
    val titleRU: String,
    val titleEN: String,
    ) {
    MAIN(if (isRussian) "База" else "Main", "Main", if (isRussian) "Бек: Восточная ударная группа" else "Beck: Mongolian Chop Squad", "Beck: Mongolian Chop Squad"),
    WATCHED(if (isRussian) "Смотрел(а)" else "Watched", "Watched", if (isRussian) "Волчица и чёрный принц" else "WOLF GIRL AND BLACK PRINCE", "WOLF GIRL AND BLACK PRINCE"),
    NOT_WATCHED(if (isRussian) "Не смотрел(а)" else "Not watched", "Not watched", if (isRussian) "Вайолет Эвергарден" else "Violet Evergarden", "Violet Evergarden"),
    WANTED(if (isRussian) "Хочу" else "Wanted", "Wanted", if (isRussian) "Крутой учитель Онидзука" else "GTO: Great Teacher Onizuka", "GTO: Great Teacher Onizuka"),
    UNWANTED(if (isRussian) "Не хочу" else "Unwanted", "Unwanted", if (isRussian) "Ковбой Бибоп" else "Cowboy Bebop", "Cowboy Bebop")
}