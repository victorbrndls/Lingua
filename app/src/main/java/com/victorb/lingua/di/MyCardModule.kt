package com.victorb.lingua.di

import com.victorb.lingua.core.mycard.repository.MyCardRepository
import com.victorb.lingua.data.mycard.repository.MyCardRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MyCardModule {

    @Binds
    @Singleton
    abstract fun myCardRepository(impl: MyCardRepositoryImpl): MyCardRepository

}