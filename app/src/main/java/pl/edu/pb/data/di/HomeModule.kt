package pl.edu.pb.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.edu.pb.data.remote.repository.HomeRepositoryImpl
import pl.edu.pb.domain.repository.HomeRepository
import pl.edu.pb.domain.usecase.GetClientsUseCase
import pl.edu.pb.domain.usecase.getClients
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    fun provideGetClientsUseCase(
        homeRepository: HomeRepository,
    ): GetClientsUseCase = GetClientsUseCase { page, size ->
        getClients(page, size, homeRepository)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {

        @Binds
        @Singleton
        fun bindHomeRepository(impl: HomeRepositoryImpl): HomeRepository
    }
}