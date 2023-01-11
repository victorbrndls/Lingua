package com.victorb.lingua.core.card.repository

import com.victorb.lingua.core.card.dto.SaveDeckCardData
import com.victorb.lingua.core.card.entity.DeckCard
import kotlinx.coroutines.flow.Flow

interface DeckCardRepository {

    suspend fun getCard(id: String): DeckCard?

    fun observeCards(deckId: String): Flow<List<DeckCard>>

    suspend fun saveCard(card: SaveDeckCardData): DeckCard

}