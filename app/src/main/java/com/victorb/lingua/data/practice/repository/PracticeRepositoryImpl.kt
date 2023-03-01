package com.victorb.lingua.data.practice.repository

import com.victorb.lingua.core.deck.repository.DeckRepository
import com.victorb.lingua.core.mycard.entity.MyCard
import com.victorb.lingua.core.mycard.entity.MyCardPractice
import com.victorb.lingua.core.mycard.repository.MyCardRepository
import com.victorb.lingua.core.practice.entity.PracticeSession
import com.victorb.lingua.core.practice.repository.PracticeRepository
import com.victorb.lingua.data.practice.PracticeSessionCreator
import com.victorb.lingua.infrastructure.logger.Logger
import java.util.*
import javax.inject.Inject

class PracticeRepositoryImpl @Inject constructor(
    private val deckRepository: DeckRepository,
    private val myCardRepository: MyCardRepository
) : PracticeRepository {

    override suspend fun getSession(deckId: String): PracticeSession? {
        val deck = deckRepository.getDeck(deckId) ?: return null

        return PracticeSessionCreator.create(
            deck, myCardRepository.getMyCards()
        )?.also { practice ->
            Logger.d("Generated practice session | id=${practice.id} cards=${practice.cards.size}")
        }
    }

    override suspend fun update(cardId: String, isCorrect: Boolean) {
        val myCard = myCardRepository.getByCardId(cardId) ?: createMyCard(cardId)

        val updatedCard = myCard.copy(
            practices = myCard.practices + listOf(
                MyCardPractice(
                    date = Date(),
                    isCorrect = isCorrect
                )
            )
        )

        Logger.d("Updated new my card | id=${updatedCard.id}, practices=${updatedCard.practices.size}")
        myCardRepository.saveMyCard(updatedCard)
    }

    private fun createMyCard(cardId: String): MyCard {
        return MyCard(
            id = UUID.randomUUID().toString(),
            cardId = cardId,
            practices = emptyList()
        )
    }

}