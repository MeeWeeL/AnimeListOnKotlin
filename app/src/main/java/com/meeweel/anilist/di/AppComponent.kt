package com.meeweel.anilist.di

//import com.meeweel.anilist.workmanager.SynchronizeWorker
import android.content.Context
import com.meeweel.anilist.newPresentation.NewMainActivity
import com.meeweel.anilist.newPresentation.mainFragment.NewMainViewModel
import com.meeweel.anilist.ui.MainActivity
import com.meeweel.anilist.ui.fragments.detailsFragment.DetailsFragment
import com.meeweel.anilist.ui.fragments.listFragments.BaseListFragment
import com.meeweel.anilist.ui.fragments.listFragments.BaseViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        RepositoryModule::class,
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
    fun inject(activity: NewMainActivity)
    fun inject(detailsFragment: DetailsFragment)
    fun inject(baseViewModel: BaseViewModel)
    fun inject(viewModel: NewMainViewModel)
    fun inject(baseListFragment: BaseListFragment)
//    fun inject(worker: SynchronizeWorker)
}