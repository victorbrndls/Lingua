package com.victorb.lingua.data.deck.repository.local.mapper

import com.victorb.lingua.core.deck.entity.Deck
import com.victorb.lingua.data.deck.repository.local.data.DeckCardData
import com.victorb.lingua.data.deck.repository.local.data.DeckData
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

    fun fromData(deck: DeckData, cards: List<DeckCardData>): Deck? {
        val deckCards = cards.mapNotNull { deckCardDataMapper.fromData(it) }

        return Deck(
            id = deck.id,
            title = deck.title ?: return null,
            cards = deckCards
        )
    }

}