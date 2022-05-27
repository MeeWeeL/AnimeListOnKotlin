package com.meeweel.anilist.ui.navigation

import com.github.terrakok.cicerone.Router
import com.meeweel.anilist.domain.models.ShortAnime

class CustomRouter : Router() {

    interface Command {
        fun execute(navigator: CustomNavigator)
    }
    
    fun openDeepLink(aniData: ShortAnime) {
        executeCommands(OpenDeepLink(aniData))
    }
}