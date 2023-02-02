package com.victorb.lingua.core.practice.entity

import com.victorb.lingua.core.card.entity.DeckCard

data class PracticeSession(
    val id: String,
    val title: String,
    val cards: List<PracticeType>
)

sealed interface PracticeType {
    val card: DeckCard

    data class TypeAnswer(override val card: DeckCard) : PracticeType
    data class MultipleOption(override val card: DeckCard, val options: List<String>) : PracticeType
}