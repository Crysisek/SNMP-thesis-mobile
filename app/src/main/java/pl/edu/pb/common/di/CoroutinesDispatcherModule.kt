package pl.edu.pb.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention
@Qualifier
annotation class DispatcherMainImmediate // UI-related

@Retention
@Qualifier
annotation class DispatcherIO // IO-related

@Retention
@Qualifier
annotation class DispatcherDefault // CPU-related

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesDispatcherModule {

    @DispatcherMainImmediate
    @Singleton
    @Provides
    fun provideDispatcherMainImmediate(): CoroutineDispatcher = Dispatchers.Main.immediate

    @DispatcherIO
    @Singleton
    @Provides
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

    @DispatcherDefault
    @Singleton
    @Provides
    fun provideDispatcherDefault(): CoroutineDispatcher = Dispatchers.Default
}