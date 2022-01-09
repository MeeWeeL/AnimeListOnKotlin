package com.meeweel.anilist.navigation

import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Forward
import com.meeweel.anilist.model.data.Anime
import com.meeweel.anilist.model.data.ShortAnime
import com.meeweel.anilist.view.fragments.detailsfragment.DetailsScreen

class OpenDeepLink(private val aniData: ShortAnime) : CustomRouter.Command, Command {

    override fun execute(navigator: CustomNavigator) {
        navigator.applyCommand(Forward(
            DetailsScreen(aniData)
        ))
    }
}