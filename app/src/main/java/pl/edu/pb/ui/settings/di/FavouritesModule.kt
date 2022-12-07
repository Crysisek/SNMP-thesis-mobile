package pl.edu.pb.ui.settings.di

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
import pl.edu.pb.ui.settings.SettingsNavigationFactory
import pl.edu.pb.ui.settings.SettingsUiState
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object SettingsViewModelModule {

    @Provides
    fun provideInitialSettingsUiState(): SettingsUiState = SettingsUiState()
}

@Module
@InstallIn(SingletonComponent::class)
interface SettingsSingletonModule {

    @Singleton
    @Binds
    @IntoSet
    fun bindSettingsNavigationFactory(factory: SettingsNavigationFactory): NavigationFactory
}
