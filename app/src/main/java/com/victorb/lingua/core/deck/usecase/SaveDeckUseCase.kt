package com.victorb.lingua.core.deck.usecase

import com.victorb.lingua.core.deck.dto.SaveDeckData
import com.victorb.lingua.core.deck.entity.Deck
import com.victorb.lingua.core.deck.repository.DeckRepository
import javax.inject.Inject

interface SaveDeckUseCase {

    suspend fun save(deck: SaveDeckData): Deck

}

class SaveDeckUseCaseImpl @Inject constructor(
    private val deckRepository: DeckRepository
) : SaveDeckUseCase {

    override suspend fun save(deck: SaveDeckData): Deck {
        return deckRepository.saveDeck(deck)
    }

}