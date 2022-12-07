package pl.edu.pb.ui.home.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import pl.edu.pb.common.navigation.NavigationFactory
import pl.edu.pb.ui.home.HomeNavigationFactory
import pl.edu.pb.ui.home.HomeUiState
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object HomeViewModelModule {

    @Provides
    fun provideInitialHomeUiState(): HomeUiState = HomeUiState()
}

@Module
@InstallIn(SingletonComponent::class)
interface HomeSingletonModule {

    @Singleton
    @Binds
    @IntoSet
    fun bindHomeNavigationFactory(factory: HomeNavigationFactory): NavigationFactory
}
