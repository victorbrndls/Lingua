package com.victorb.lingua.di

import com.victorb.lingua.core.practice.repository.PracticeRepository
import com.victorb.lingua.core.practice.usecase.CheckPracticeAnswerUseCase
import com.victorb.lingua.core.practice.usecase.CheckPracticeAnswerUseCaseImpl
import com.victorb.lingua.core.practice.usecase.GetPracticeSessionUseCase
import com.victorb.lingua.core.practice.usecase.GetPracticeSessionUseCaseImpl
import com.victorb.lingua.data.deck.repository.DeckRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PracticeModule {

    @Binds
    @Singleton
    abstract fun practiceRepository(impl: DeckRepositoryImpl): PracticeRepository

    @Binds
    @Singleton
    abstract fun getPracticeSessionUseCase(
        impl: GetPracticeSessionUseCaseImpl
    ): GetPracticeSessionUseCase

    @Binds
    @Singleton
    abstract fun checkPracticeAnswerUseCase(
        impl: CheckPracticeAnswerUseCaseImpl
    ): CheckPracticeAnswerUseCase

}