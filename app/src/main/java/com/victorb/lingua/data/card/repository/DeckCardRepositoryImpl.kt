package com.victorb.lingua.data.card.repository

import com.victorb.lingua.core.card.dto.SaveDeckCardData
import com.victorb.lingua.core.card.entity.DeckCard
import com.victorb.lingua.core.card.repository.DeckCardRepository
import java.util.*
import javax.inject.Inject

class DeckCardRepositoryImpl @Inject constructor() : DeckCardRepository {

    private val cards = mutableListOf<DeckCard>()

    override suspend fun get(id: String): DeckCard? {
        return cards.find { it.id == id }
    }

    override suspend fun save(card: SaveDeckCardData): DeckCard {
        val entity = DeckCard(
            card.cardId ?: UUID.randomUUID().toString(),
            card.deckId,
            card.input,
            card.outputs,
        )

        cards.add(entity)
        return entity
    }

}