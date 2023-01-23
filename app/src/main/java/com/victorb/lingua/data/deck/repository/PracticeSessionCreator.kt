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

        val sortedCards = availableMyCards
            .filter { (_, review) -> review <= now }
            .sortedBy { (_, review) -> review.time }
            .map { it.first }
            .take(8)

        if (sortedCards.isEmpty()) return null

        return PracticeSession(
            id = UUID.randomUUID().toString(),
            title = deck.title,
            cards = sortedCards
        )
    }

    private fun List<Pair<DeckCard, MyCard?>>.mapToNextReviewDate(): List<Pair<DeckCard, Date>> {
        return map { (card, myCard) -> card to getNextReviewDate(card, myCard) }
    }

    fun getNextReviewDate(card: DeckCard, myCard: MyCard?): Date {
        // If card has never been reviewed, review it now
        if (myCard == null || myCard.practices.isEmpty()) return Date(0)

        val practices = myCard.practices.sortedBy { it.date }

        // If user got last answer wrong, review 8hrs after practice
        val lastPractice = practices.last()
        if (!lastPractice.isCorrect) {
            return lastPractice.date.plusHours(8)
        }

        // If user got last answer right, just review in a few hours. This is simplified for now
        return Date().plusHours(Random.nextInt(12, 40))
    }

}
