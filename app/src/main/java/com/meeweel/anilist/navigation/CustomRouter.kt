package com.meeweel.anilist.navigation

import com.github.terrakok.cicerone.Router
import com.meeweel.anilist.model.data.Anime
import com.meeweel.anilist.model.data.ShortAnime

class CustomRouter : Router() {

    interface Command {
        fun execute(navigator: CustomNavigator)
    }
    
    fun openDeepLink(aniData: ShortAnime) {
        executeCommands(OpenDeepLink(aniData))
    }
}