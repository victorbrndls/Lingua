package com.victorb.lingua.data.mydeck.repository

import com.victorb.lingua.core.deck.repository.DeckRepository
import com.victorb.lingua.core.mycard.repository.MyCardRepository
import com.victorb.lingua.core.mydeck.entity.MyDeck
import com.victorb.lingua.core.mydeck.repository.MyDeckRepository
import com.victorb.lingua.data.practice.PracticeSessionCreator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.*
import javax.inject.Inject

class MyDeckRepositoryImpl @Inject constructor(
    private val deckRepository: DeckRepository,
    private val myCardRepository: MyCardRepository
) : MyDeckRepository {

    override fun observeMyDecks(): Flow<List<MyDeck>> {
        return combine(
            deckRepository.observeAll(),
            myCardRepository.observeMyCards()
        ) { decks, myCards ->
            decks.map { deck ->
                val cards = deck.cards.map { card ->
                    card to myCards.find { myCard -> myCard.cardId == card.id }
                }

                val learnedCards = cards.count { (_, myCard) ->
                    if (myCard == null) return@count false
                    myCard.practices.any { it.isCorrect }
                }

                val now = Date()
                val cardToReview = cards.count { (_, myCard) ->
                    val date = PracticeSessionCreator.getNextReviewDate(myCard)
                    date <= now
                }

                MyDeck(
                    id = UUID.randomUUID().toString(),
                    deckId = deck.id,
                    title = deck.title,
                    cardsToReview = cardToReview,
                    learnedCards = learnedCards,
                    totalCards = deck.cards.size,
                )
            }
        }
    }
}