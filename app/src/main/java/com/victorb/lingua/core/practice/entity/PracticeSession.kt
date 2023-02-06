package com.victorb.lingua.core.practice.entity

import com.victorb.lingua.core.card.entity.DeckCard

data class PracticeSession(
    val id: String,
    val title: String,
    val cards: List<CardPractice>
)

sealed interface CardPractice {
    val card: DeckCard // refers to the card being practiced

    data class InputField(override val card: DeckCard) : CardPractice

    data class MultipleTextOptions(
        override val card: DeckCard, val options: List<String>
    ) : CardPractice
}