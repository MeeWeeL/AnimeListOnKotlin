package com.meeweel.anilist.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.meeweel.anilist.ui.navigation.CustomRouter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CiceroneModule {

    var cicerone: Cicerone<CustomRouter> = Cicerone.create(CustomRouter())

    @Provides
    fun cicerone(): Cicerone<CustomRouter> = cicerone

    @Singleton
    @Provides
    fun navigatorHolder(): NavigatorHolder = cicerone.getNavigatorHolder()

    @Singleton
    @Provides
    fun router(): CustomRouter = cicerone.router
}