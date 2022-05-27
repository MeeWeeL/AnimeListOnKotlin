package com.meeweel.anilist.ui.navigation

import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Forward
import com.meeweel.anilist.domain.models.ShortAnime
import com.meeweel.anilist.ui.fragments.detailsfragment.DetailsScreen

class OpenDeepLink(private val aniData: ShortAnime) : CustomRouter.Command, Command {

    override fun execute(navigator: CustomNavigator) {
        navigator.applyCommand(Forward(
            DetailsScreen(aniData)
        ))
    }
}