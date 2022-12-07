package pl.edu.pb.ui.search.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import pl.edu.pb.common.navigation.NavigationFactory
import pl.edu.pb.ui.search.SearchNavigationFactory
import pl.edu.pb.ui.search.SearchUiState
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object SearchViewModelModule {

    @Provides
    fun provideInitialSearchUiState(): SearchUiState = SearchUiState()
}

@Module
@InstallIn(SingletonComponent::class)
interface SearchSingletonModule {

    @Singleton
    @Binds
    @IntoSet
    fun bindSearchNavigationFactory(factory: SearchNavigationFactory): NavigationFactory
}
