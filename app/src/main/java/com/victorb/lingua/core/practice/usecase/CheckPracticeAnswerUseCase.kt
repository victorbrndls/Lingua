package com.victorb.lingua.core.practice.usecase

import com.victorb.lingua.core.card.entity.DeckCard
import com.victorb.lingua.core.practice.usecase.CheckPracticeAnswerResponse.Correct
import com.victorb.lingua.core.practice.usecase.CheckPracticeAnswerResponse.Wrong
import javax.inject.Inject

interface CheckPracticeAnswerUseCase {

    fun checkAnswer(card: DeckCard, answer: String): CheckPracticeAnswerResponse

}

class CheckPracticeAnswerUseCaseImpl @Inject constructor() : CheckPracticeAnswerUseCase {

    private val charsToIgnore = Regex("[?!,.;\"']")

    override fun checkAnswer(card: DeckCard, answer: String): CheckPracticeAnswerResponse {
        val normalizedAnswer = answer.normalize()
        val normalizedOutputs = card.outputs.normalize()

        if (normalizedAnswer.isBlank())
            return Wrong
        if (normalizedOutputs.any { it == normalizedAnswer })
            return Correct(isExactAnswer = card.outputs.any { it == answer })

        return Wrong
    }

    private fun String.normalize() = trim().replace(charsToIgnore, "")
    private fun List<String>.normalize() = map { it.normalize() }

}

sealed interface CheckPracticeAnswerResponse {
    data class Correct(val isExactAnswer: Boolean) : CheckPracticeAnswerResponse
    object Wrong : CheckPracticeAnswerResponse

    fun isCorrect() = this is Correct
}