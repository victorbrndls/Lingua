package com.victorb.lingua.core.practice.usecase

import com.victorb.lingua.core.card.entity.DeckCard
import javax.inject.Inject
import kotlin.random.Random

interface CheckPracticeAnswerUseCase {

    fun checkAnswer(card: DeckCard, answer: String): Boolean

}

class CheckPracticeAnswerUseCaseImpl @Inject constructor() : CheckPracticeAnswerUseCase {

    override fun checkAnswer(card: DeckCard, answer: String): Boolean {
        return Random.nextBoolean()
    }

}