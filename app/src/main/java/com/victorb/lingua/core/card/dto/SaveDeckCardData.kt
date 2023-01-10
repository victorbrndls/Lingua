package com.victorb.lingua.core.card.dto

data class SaveDeckCardData(
    val cardId: String?,
    val deckId: String,
    val input: String,
    val outputs: List<String>
)