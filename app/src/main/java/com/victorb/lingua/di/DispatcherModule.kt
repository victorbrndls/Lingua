package com.victorb.lingua.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IODispatcher

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatcherModule {

    companion object {
        @Provides
        @Singleton
        @IODispatcher
        fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO
    }

}