package com.victorb.lingua.di

import com.victorb.lingua.core.mycard.repository.MyCardRepository
import com.victorb.lingua.data.mycard.repository.MyCardRepositoryImpl
import com.victorb.lingua.data.mycard.repository.local.dao.MyCardDao
import com.victorb.lingua.infrastructure.database.room.AppDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MyCardModule {

    companion object {
        @Provides
        @Singleton
        fun myCardDao(database: AppDatabase): MyCardDao = database.myCard()
    }

    @Binds
    @Singleton
    abstract fun myCardRepository(impl: MyCardRepositoryImpl): MyCardRepository

}