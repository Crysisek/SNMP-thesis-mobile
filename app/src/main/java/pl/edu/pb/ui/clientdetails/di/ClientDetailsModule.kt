package pl.edu.pb.ui.clientdetails.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import pl.edu.pb.common.navigation.NavigationFactory
import pl.edu.pb.ui.clientdetails.ClientDetailsNavigationFactory
import pl.edu.pb.ui.clientdetails.ClientDetailsUiState
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object ClientDetailsViewModelModule {

    @Provides
    fun provideInitialClientDetailsState() : ClientDetailsUiState = ClientDetailsUiState()
}

@Module
@InstallIn(SingletonComponent::class)
interface ClientDetailsSingletonModule {

    @Singleton
    @Binds
    @IntoSet
    fun bindClientDetailsNavigationFactory(factory: ClientDetailsNavigationFactory): NavigationFactory
}