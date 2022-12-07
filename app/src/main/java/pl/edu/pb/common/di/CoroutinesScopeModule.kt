package pl.edu.pb.common.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention
@Qualifier
annotation class ScopeMainImmediate // UI-related

@Retention
@Qualifier
annotation class ScopeIO // IO-related

@Retention
@Qualifier
annotation class ScopeDefault // CPU-related

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesScopeModule {
    private const val TAG = "CoroutineException"

    @Singleton
    @Provides
    fun provideCoroutineExceptionHandler(): CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.message?.let { Log.e(TAG, it) }
    }

    @ScopeMainImmediate
    @Singleton
    @Provides
    fun provideScopeMainImmediate(
        @DispatcherMainImmediate mainImmediateDispatcher: CoroutineDispatcher,
        exceptionHandler: CoroutineExceptionHandler,
    ): CoroutineScope = CoroutineScope(SupervisorJob() + mainImmediateDispatcher + exceptionHandler)

    @ScopeIO
    @Singleton
    @Provides
    fun provideScopeIO(
        @DispatcherIO ioDispatcher: CoroutineDispatcher,
        exceptionHandler: CoroutineExceptionHandler,
    ): CoroutineScope = CoroutineScope(SupervisorJob() + ioDispatcher + exceptionHandler)

    @ScopeDefault
    @Singleton
    @Provides
    fun provideScopeDefault(
        @DispatcherDefault defaultDispatcher: CoroutineDispatcher,
        exceptionHandler: CoroutineExceptionHandler,
    ): CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher + exceptionHandler)
}

