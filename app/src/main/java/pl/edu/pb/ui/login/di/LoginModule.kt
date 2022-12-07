package pl.edu.pb.ui.login.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import pl.edu.pb.common.navigation.NavigationFactory
import pl.edu.pb.ui.login.LoginNavigationFactory
import pl.edu.pb.ui.login.LoginUiState
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object LoginViewModelModule {

    @Provides
    fun provideInitialLoginUiState(): LoginUiState = LoginUiState()
}

@Module
@InstallIn(SingletonComponent::class)
interface LoginSingletonModule {

    @Singleton
    @Binds
    @IntoSet
    fun bindLoginNavigationFactory(factory: LoginNavigationFactory): NavigationFactory
}
