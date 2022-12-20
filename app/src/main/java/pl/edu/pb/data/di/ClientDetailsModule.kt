package pl.edu.pb.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.edu.pb.data.remote.repository.StatusRepositoryImpl
import pl.edu.pb.domain.repository.StatusRepository
import pl.edu.pb.domain.usecase.GetStatusesUseCase
import pl.edu.pb.domain.usecase.getStatuses
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ClientDetailsModule {

    @Provides
    fun provideGetStatusesUseCase(
        statusRepository: StatusRepository,
    ): GetStatusesUseCase = GetStatusesUseCase { clientId, page, sortBy, sortDir ->
        getStatuses(clientId, page, sortBy, sortDir, statusRepository)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {

        @Binds
        @Singleton
        fun bindStatusRepository(impl: StatusRepositoryImpl): StatusRepository
    }
}
