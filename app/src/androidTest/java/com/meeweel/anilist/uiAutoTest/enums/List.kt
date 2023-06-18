package com.meeweel.anilist.uiAutoTest.enums

enum class List(
    val text: String,
    val title: String
    ) {
    MAIN("База","Бек: Восточная ударная группа"),
    WATCHED("Смотрел(а)","Волчица и чёрный принц"),
    NOT_WATCHED("Не смотрел(а)","Вайолет Эвергарден"),
    WANTED("Хочу","Крутой учитель Онидзука"),
    UNWANTED("Не хочу","Ковбой Бибоп")
}