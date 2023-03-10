package com.victorb.lingua.data.deck.repository.local.mapper

import com.victorb.lingua.core.deck.entity.Deck
import com.victorb.lingua.data.deck.repository.local.data.DeckCardData
import com.victorb.lingua.data.deck.repository.local.data.DeckData
import com.victorb.lingua.data.deck.repository.local.dto.DeckCardOutput
import javax.inject.Inject

class DeckDataMapper @Inject constructor(
    private val deckCardDataMapper: DeckCardDataMapper
) {

    fun toData(deck: Deck): DeckData {
        return DeckData(
            id = deck.id,
            title = deck.title
        )
    }

    fun fromData(deck: DeckData, cards: List<DeckCardData>, outputs: List<DeckCardOutput>): Deck? {
        val deckCards = cards.mapNotNull { card ->
            deckCardDataMapper.fromData(
                card = card,
                outputs = outputs.filter { it.cardId == card.id }
            )
        }

        return Deck(
            id = deck.id,
            title = deck.title ?: return null,
            cards = deckCards
        )
    }

}