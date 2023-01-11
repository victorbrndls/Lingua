package com.victorb.lingua.core.deck.entity

import com.victorb.lingua.core.card.entity.DeckCard

data class Deck(
    val id: String,
    val cards: List<DeckCard>
)