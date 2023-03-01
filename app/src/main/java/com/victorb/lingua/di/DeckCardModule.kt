package com.victorb.lingua.di

import com.victorb.lingua.core.card.repository.DeckCardRepository
import com.victorb.lingua.core.card.usecase.*
import com.victorb.lingua.data.deck.repository.DeckRepositoryImpl
import com.victorb.lingua.data.deck.repository.local.dao.DeckCardDao
import com.victorb.lingua.data.deck.repository.local.dao.DeckCardOutputDao
import com.victorb.lingua.infrastructure.database.room.AppDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DeckCardModule {

    companion object {
        @Provides
        @Singleton
        fun deckCardDao(database: AppDatabase): DeckCardDao = database.deckCardDao()

        @Provides
        @Singleton
        fun deckCardOutputDao(
            database: AppDatabase
        ): DeckCardOutputDao = database.deckCardOutputDao()
    }

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