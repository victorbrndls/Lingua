package com.victorb.lingua.core.card.entity

data class DeckCard(
    val id: String,
    val deckId: String,
    val input: String,
    val outputs: List<String>
)