package com.victorb.lingua.core.mycard.entity

import java.util.*

data class MyCard(
    val id: String,
    val cardId: String,
    val practices: List<MyCardPractice>,
)

data class MyCardPractice(
    val date: Date,
    val isCorrect: Boolean
)