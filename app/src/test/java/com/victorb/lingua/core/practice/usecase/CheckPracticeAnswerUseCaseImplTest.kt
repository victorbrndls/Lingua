package com.victorb.lingua.core.practice.usecase

import com.victorb.lingua.core.card.entity.DeckCard
import com.victorb.lingua.core.practice.usecase.CheckPracticeAnswerResponse.Correct
import com.victorb.lingua.core.practice.usecase.CheckPracticeAnswerResponse.Wrong
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

internal class CheckPracticeAnswerUseCaseImplTest {

    private lateinit var useCase: CheckPracticeAnswerUseCase

    private val card1 = card(
        input = "Potrebbe aiutarmi, per favore?",
        outputs = listOf("Could you help me, please?"),
    )

    private val card2 = card(
        input = "Come ti chiami?",
        outputs = listOf("What is your name?", "What's your name?"),
    )

    @Before
    fun setup() {
        useCase = CheckPracticeAnswerUseCaseImpl()
    }

    @Test
    fun emptyAnswerIsWrong() {
        val answer = ""

        val result = useCase.checkAnswer(card1, answer)

        assertEquals(Wrong, result)
    }

    @Test
    fun answerWithoutQuestionMarkIsCorrect() {
        val answer = "Could you help me, please"

        val result = useCase.checkAnswer(card1, answer)

        assertEquals(Correct(isExactAnswer = true), result)
    }

    @Test
    fun answerWithoutCommaIsCorrect() {
        val answer = "Could you help me please"

        val result = useCase.checkAnswer(card1, answer)

        assertEquals(Correct(isExactAnswer = true), result)
    }

    @Test
    fun answerWithTwoOutputsIsCorrect() {
        val answer = "What's your name?"

        val result = useCase.checkAnswer(card2, answer)

        assertEquals(Correct(isExactAnswer = true), result)
    }

    @Test
    fun answerWithTwoOutputsIsWrong() {
        val answer = "What's my dog's name?"

        val result = useCase.checkAnswer(card2, answer)

        assertEquals(Wrong, result)
    }

    @Test
    fun approximateAnswerIsCorrect() {
        val answers = listOf(
            "What is you nam",
            "What is your n",
            "What your name",
            "What's your name",
        )

        answers.forEach { answer ->
            val result = useCase.checkAnswer(card2, answer)

            assertEquals(Correct(isExactAnswer = false), result)
        }
    }

    private fun card(
        input: String,
        outputs: List<String>,
    ): DeckCard = DeckCard(
        id = UUID.randomUUID().toString(),
        deckId = "",
        input = input,
        outputs = outputs,
    )

}