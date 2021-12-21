package com.meeweel.anilist.navigation

import com.github.terrakok.cicerone.Router
import com.meeweel.anilist.model.data.Anime

class CustomRouter : Router() {

    interface Command {
        fun execute(navigator: CustomNavigator)
    }
    
    fun openDeepLink(aniData: Anime) {
        executeCommands(OpenDeepLink(aniData))
    }
}