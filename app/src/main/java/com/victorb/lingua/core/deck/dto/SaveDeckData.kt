package com.victorb.lingua.core.deck.dto

data class SaveDeckData(
    val deckId: String,
    val title: String,
    val cards: List<SaveDeckCardData>
)

data class SaveDeckCardData(
    val cardId: String,
    val position: Int
)