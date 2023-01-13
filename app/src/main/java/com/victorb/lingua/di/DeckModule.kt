package com.victorb.lingua.di

import com.victorb.lingua.core.card.repository.DeckCardRepository
import com.victorb.lingua.core.deck.repository.DeckRepository
import com.victorb.lingua.core.deck.usecase.*
import com.victorb.lingua.data.deck.repository.DeckRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DeckModule {

    @Binds
    @Singleton
    abstract fun deckCardRepository(impl: DeckRepositoryImpl): DeckCardRepository

    @Binds
    @Singleton
    abstract fun deckRepository(impl: DeckRepositoryImpl): DeckRepository

    @Binds
    @Singleton
    abstract fun saveDeckUseCase(impl: SaveDeckUseCaseImpl): SaveDeckUseCase

    @Binds
    @Singleton
    abstract fun getDeckUseCase(impl: GetDeckUseCaseImpl): GetDeckUseCase

    @Binds
    @Singleton
    abstract fun getDecksUseCase(impl: GetDecksUseCaseImpl): GetDecksUseCase

}