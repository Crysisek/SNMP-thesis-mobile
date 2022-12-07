package pl.edu.pb.ui.favourites.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import pl.edu.pb.common.navigation.NavigationFactory
import pl.edu.pb.ui.favourites.FavouritesNavigationFactory
import pl.edu.pb.ui.favourites.FavouritesUiState
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object FavouritesViewModelModule {

    @Provides
    fun provideInitialFavouritesUiState(): FavouritesUiState = FavouritesUiState()
}

@Module
@InstallIn(SingletonComponent::class)
interface FavouritesSingletonModule {

    @Singleton
    @Binds
    @IntoSet
    fun bindFavouritesNavigationFactory(factory: FavouritesNavigationFactory): NavigationFactory
}
