package com.victorb.lingua.core.mydeck.entity

data class MyDeck(
    val id: String,
    val title: String,
    val learnedCards: Int,
    val totalCards: Int,
)