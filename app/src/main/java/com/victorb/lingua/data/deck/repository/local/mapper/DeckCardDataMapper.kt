package com.victorb.lingua.data.deck.repository.local.mapper

import com.victorb.lingua.core.card.entity.DeckCard
import com.victorb.lingua.data.deck.repository.local.data.DeckCardData
import javax.inject.Inject

class DeckCardDataMapper @Inject constructor() {

    fun toData(card: DeckCard): DeckCardData {
        return DeckCardData(
            id = card.id,
            deckId = card.deckId,
            input = card.input
        )
    }

    fun fromData(card: DeckCardData): DeckCard? {
        return DeckCard(
            id = card.id,
            deckId = card.deckId,
            input = card.input ?: return null,
            outputs = emptyList()
        )
    }

}