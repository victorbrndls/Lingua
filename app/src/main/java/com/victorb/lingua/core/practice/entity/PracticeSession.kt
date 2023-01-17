package com.victorb.lingua.core.practice.entity

import com.victorb.lingua.core.card.entity.DeckCard

data class PracticeSession(
    val id: String,
    val title: String,
    val cards: List<DeckCard>
)