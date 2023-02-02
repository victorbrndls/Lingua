package com.victorb.lingua.core.practice.usecase

import com.victorb.lingua.core.card.entity.DeckCard
import com.victorb.lingua.core.practice.usecase.CheckPracticeAnswerResponse.Correct
import com.victorb.lingua.core.practice.usecase.CheckPracticeAnswerResponse.Wrong
import javax.inject.Inject

private const val WORD_DIFF_THRESHOLD = 0.2

interface CheckPracticeAnswerUseCase {

    fun checkAnswer(card: DeckCard, answer: String): CheckPracticeAnswerResponse

}

class CheckPracticeAnswerUseCaseImpl @Inject constructor(
    private val wordDistanceCalculator: WordDistanceCalculator
) : CheckPracticeAnswerUseCase {

    private val charsToIgnore = Regex("[?!,.;\"']")

    override fun checkAnswer(card: DeckCard, answer: String): CheckPracticeAnswerResponse {
        val normalizedAnswer = answer.normalize()
        val normalizedOutputs = card.outputs.normalize()

        if (normalizedAnswer.isBlank())
            return Wrong
        if (normalizedOutputs.any { it == normalizedAnswer })
            return Correct(isExactAnswer = true)

        normalizedOutputs.forEach { output ->
            val distance = wordDistanceCalculator.calculate(normalizedAnswer, output)
            val threshold = (output.length * WORD_DIFF_THRESHOLD).toInt()
            if (distance <= threshold) return Correct(isExactAnswer = false)
        }

        return Wrong
    }

    private fun String.normalize() = trim().replace(charsToIgnore, "").lowercase()
    private fun List<String>.normalize() = map { it.normalize() }

}

sealed interface CheckPracticeAnswerResponse {
    data class Correct(val isExactAnswer: Boolean) : CheckPracticeAnswerResponse
    object Wrong : CheckPracticeAnswerResponse

    val isCorrect: Boolean
        get() = this is Correct
}