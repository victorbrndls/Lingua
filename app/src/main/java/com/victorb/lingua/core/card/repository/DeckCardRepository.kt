package com.victorb.lingua.core.card.repository

import com.victorb.lingua.core.card.entity.DeckCard
import com.victorb.lingua.core.card.dto.SaveDeckCardData

interface DeckCardRepository {

    suspend fun get(id: String): DeckCard?

    suspend fun save(card: SaveDeckCardData): DeckCard

}