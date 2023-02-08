package com.victorb.lingua.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

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