package com.victorb.lingua.di

import com.victorb.lingua.core.mydeck.repository.MyDeckRepository
import com.victorb.lingua.core.mydeck.usecase.MyDecksUseCase
import com.victorb.lingua.core.mydeck.usecase.MyDecksUseCaseImpl
import com.victorb.lingua.data.mydeck.repository.MyDeckRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MyDeckModule {

    @Binds
    @Singleton
    abstract fun myDeckRepository(impl: MyDeckRepositoryImpl): MyDeckRepository

    @Binds
    @Singleton
    abstract fun myDecksUseCase(impl: MyDecksUseCaseImpl): MyDecksUseCase

}