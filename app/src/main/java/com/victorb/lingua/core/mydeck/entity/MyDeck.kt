package com.victorb.lingua.core.mydeck.entity

data class MyDeck(
    val id: String,
    val deckId: String,
    val title: String,
    val cardsToReview: Int,
    val learnedCards: Int,
    val totalCards: Int,
)