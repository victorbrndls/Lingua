package com.victorb.lingua.di

import com.victorb.lingua.infrastructure.date.DateTranslator
import com.victorb.lingua.infrastructure.date.DateTranslatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class InfrastructureModule {

    @Binds
    @Singleton
    abstract fun dateTranslator(impl: DateTranslatorImpl): DateTranslator

}