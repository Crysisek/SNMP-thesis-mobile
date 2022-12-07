package pl.edu.pb.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.edu.pb.data.remote.api.ServerApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServerModule {

    @Provides
    @Singleton
    fun provideServerApi(
        retrofit: Retrofit,
    ): ServerApi = retrofit.create(ServerApi::class.java)
}