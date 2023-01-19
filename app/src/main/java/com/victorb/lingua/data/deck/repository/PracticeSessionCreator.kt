package com.victorb.lingua.data.deck.repository

import com.victorb.lingua.core.card.entity.DeckCard
import com.victorb.lingua.core.deck.entity.Deck
import com.victorb.lingua.core.mycard.entity.MyCard
import com.victorb.lingua.core.practice.entity.PracticeSession
import com.victorb.lingua.infrastructure.ktx.plusHours
import java.util.*
import kotlin.random.Random

// This is a temporary class on the client, in the future it'll be in the backend
object PracticeSessionCreator {

    fun create(deck: Deck, myCards: List<MyCard>): PracticeSession? {
        val availableMyCards = deck.cards
            .map { card ->
                card to myCards.find { it.cardId == card.id }
            }
            .mapToNextReviewDate()

        val now = Date()

        val sortedCards = availableMyCards.sortedBy { (_, review) ->
            // if review time is in the future, the result is positive
            // if review time is in the past, the result is negative
            review.time - now.time
        }.map { it.first }

        return PracticeSession(
            id = UUID.randomUUID().toString(),
            title = deck.title,
            cards = sortedCards
        )
    }

    private fun List<Pair<DeckCard, MyCard?>>.mapToNextReviewDate(): List<Pair<DeckCard, Date>> {
        return map { (card, myCard) ->
            // If card has never been reviewed, review it now
            if (myCard == null || myCard.practices.isEmpty()) return@map card to Date()

            val practices = myCard.practices.sortedBy { it.date }

            // If user got last answer wrong, review 8hrs after practice
            val lastPractice = practices.last()
            if (!lastPractice.isCorrect) {
                return@map card to lastPractice.date.plusHours(8)
            }

            // If user got last answer right, just review in a few hours. This is simplified for now
            card to Date().plusHours(Random.nextInt(12, 40))
        }
    }

}
