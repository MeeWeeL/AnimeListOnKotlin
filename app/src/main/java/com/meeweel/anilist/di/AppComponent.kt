package com.meeweel.anilist.di

import android.content.Context
import com.meeweel.anilist.view.MainActivity
import com.meeweel.anilist.view.fragments.baselistfragment.BaseListFragment
import com.meeweel.anilist.view.fragments.baselistfragment.BaseViewModel
import com.meeweel.anilist.view.fragments.detailsfragment.DetailsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        RepositoryModule::class,
        CiceroneModule::class,
        SynchronizerModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun setContext(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(detailsFragment: DetailsFragment)
    fun inject(baseViewModel: BaseViewModel)
    fun inject(baseListFragment: BaseListFragment)
}