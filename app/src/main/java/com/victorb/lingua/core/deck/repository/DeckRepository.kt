package com.victorb.lingua.core.deck.repository

import com.victorb.lingua.core.deck.dto.SaveDeckData
import com.victorb.lingua.core.deck.entity.Deck

interface DeckRepository {

    suspend fun getDeck(id: String): Deck?
    suspend fun getDecks(): List<Deck>

    suspend fun saveDeck(deck: SaveDeckData): Deck

}