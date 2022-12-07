package pl.edu.pb.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.edu.pb.data.remote.api.ServerApi
import pl.edu.pb.data.remote.repository.LoginRepositoryImpl
import pl.edu.pb.domain.repository.LoginRepository
import pl.edu.pb.domain.usecase.CheckServerUpUseCase
import pl.edu.pb.domain.usecase.LoginUseCase
import pl.edu.pb.domain.usecase.checkIfServerIsUp
import pl.edu.pb.domain.usecase.login
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Provides
    fun provideCheckServerUpUseCase(
        loginRepository: LoginRepository,
    ): CheckServerUpUseCase = CheckServerUpUseCase {
        checkIfServerIsUp(loginRepository)
    }

    @Provides
    fun provideLoginUseCase(
        loginRepository: LoginRepository,
    ): LoginUseCase = LoginUseCase {
        login(loginRepository)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {

        @Binds
        @Singleton
        fun bindLoginRepository(impl: LoginRepositoryImpl): LoginRepository
    }
}