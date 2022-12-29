package pl.edu.pb.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.edu.pb.data.remote.repository.SearchRepositoryImpl
import pl.edu.pb.domain.repository.SearchRepository
import pl.edu.pb.domain.usecase.SearchForClientsUseCase
import pl.edu.pb.domain.usecase.searchClients
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {

    @Provides
    fun provideSearchClientsUseCase(
        searchRepository: SearchRepository,
    ): SearchForClientsUseCase = SearchForClientsUseCase {
        searchClients(it, searchRepository)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {

        @Binds
        @Singleton
        fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository
    }
}