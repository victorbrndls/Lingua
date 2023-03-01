package com.victorb.lingua.di

import com.victorb.lingua.core.practice.repository.PracticeRepository
import com.victorb.lingua.core.practice.usecase.*
import com.victorb.lingua.data.practice.repository.PracticeRepositoryImpl
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
    abstract fun practiceRepository(impl: PracticeRepositoryImpl): PracticeRepository

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

    @Binds
    @Singleton
    abstract fun practiceCardUseCase(impl: PracticeCardUseCaseImpl): PracticeCardUseCase

}