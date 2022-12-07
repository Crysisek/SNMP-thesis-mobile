package pl.edu.pb.data.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.edu.pb.data.remote.repository.DataStoreRepositoryImpl
import pl.edu.pb.data.remote.repository.LoginRepositoryImpl
import pl.edu.pb.domain.repository.DataStoreRepository
import pl.edu.pb.domain.repository.LoginRepository
import pl.edu.pb.domain.usecase.GetCredentialsUseCase
import pl.edu.pb.domain.usecase.SaveCredentialsUseCase
import pl.edu.pb.domain.usecase.getCredentials
import pl.edu.pb.domain.usecase.saveCredentials
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

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {

        @Binds
        @Singleton
        fun bindDataStoreRepository(impl: DataStoreRepositoryImpl): DataStoreRepository
    }
}