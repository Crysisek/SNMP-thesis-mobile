package pl.edu.pb.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.edu.pb.data.remote.repository.DataStoreRepositoryImpl
import pl.edu.pb.domain.repository.DataStoreRepository
import pl.edu.pb.domain.usecase.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    fun provideSaveCredentialsUseCase(
        dataStoreRepository: DataStoreRepository,
    ): SaveCredentialsUseCase = SaveCredentialsUseCase { keys, values ->
        saveCredentials(keys, values, dataStoreRepository)
    }

    @Provides
    fun provideGetCredentialsUseCase(
        dataStoreRepository: DataStoreRepository,
    ): GetCredentialsUseCase = GetCredentialsUseCase {
        getCredentials(it, dataStoreRepository)
    }

    @Provides
    fun provideSaveRefreshIntervalsUseCase(
        dataStoreRepository: DataStoreRepository,
    ): SaveRefreshIntervalUseCase = SaveRefreshIntervalUseCase { key, value ->
        saveRefreshInterval(key, value, dataStoreRepository)
    }

    @Provides
    fun provideGetRefreshIntervalUseCase(
        dataStoreRepository: DataStoreRepository,
    ): GetRefreshIntervalUseCase = GetRefreshIntervalUseCase {
        getRefreshInterval(it, dataStoreRepository)
    }

    @Provides
    fun provideGetRefreshStateUseCase(
        dataStoreRepository: DataStoreRepository,
    ): GetRefreshStateUseCase = GetRefreshStateUseCase {
        getRefreshState(it, dataStoreRepository)
    }

    @Provides
    fun provideSaveRefreshStateUseCase(
        dataStoreRepository: DataStoreRepository,
    ): SaveRefreshStateUseCase = SaveRefreshStateUseCase { key, value ->
        saveRefreshState(key, value, dataStoreRepository)
    }

    @Provides
    fun provideGetCustomUsernameUseCase(
        dataStoreRepository: DataStoreRepository,
    ): GetCustomUsernameUseCase = GetCustomUsernameUseCase {
        getCustomUsername(it, dataStoreRepository)
    }

    @Provides
    fun provideSaveCustomUsernameUseCase(
        dataStoreRepository: DataStoreRepository,
    ): SaveCustomUsernameUseCase = SaveCustomUsernameUseCase { key, value ->
        saveCustomUsername(key, value, dataStoreRepository)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {

        @Binds
        @Singleton
        fun bindDataStoreRepository(impl: DataStoreRepositoryImpl): DataStoreRepository
    }
}