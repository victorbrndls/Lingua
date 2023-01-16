package com.victorb.lingua.di

import com.victorb.lingua.core.card.repository.DeckCardRepository
import com.victorb.lingua.core.card.usecase.*
import com.victorb.lingua.data.deck.repository.DeckRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DeckCardModule {
    
    @Binds
    @Singleton
    abstract fun deckCardRepository(impl: DeckRepositoryImpl): DeckCardRepository

    @Binds
    @Singleton
    abstract fun saveDeckCardUseCase(impl: SaveCardUseCaseImpl): SaveDeckCardUseCase

    @Binds
    @Singleton
    abstract fun observerDeckCardUseCase(
        impl: ObserverDeckCardsUseCaseImpl
    ): ObserverDeckCardsUseCase

    @Binds
    @Singleton
    abstract fun getDeckCardUseCase(impl: GetDeckCardUseCaseImpl): GetDeckCardUseCase

}