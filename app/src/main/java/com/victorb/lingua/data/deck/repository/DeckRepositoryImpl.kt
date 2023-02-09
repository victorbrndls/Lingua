package com.victorb.lingua.data.deck.repository

import com.victorb.lingua.core.card.dto.SaveDeckCardData
import com.victorb.lingua.core.card.entity.DeckCard
import com.victorb.lingua.core.card.repository.DeckCardRepository
import com.victorb.lingua.core.deck.dto.SaveDeckData
import com.victorb.lingua.core.deck.entity.Deck
import com.victorb.lingua.core.deck.repository.DeckRepository
import com.victorb.lingua.core.mycard.entity.MyCard
import com.victorb.lingua.core.mycard.entity.MyCardPractice
import com.victorb.lingua.core.mycard.repository.MyCardRepository
import com.victorb.lingua.core.mydeck.entity.MyDeck
import com.victorb.lingua.core.mydeck.repository.MyDeckRepository
import com.victorb.lingua.core.practice.entity.PracticeSession
import com.victorb.lingua.core.practice.repository.PracticeRepository
import com.victorb.lingua.data.deck.repository.local.LocalDeckCardDataSource
import com.victorb.lingua.data.deck.repository.local.LocalDeckDataSource
import com.victorb.lingua.infrastructure.ktx.replaceOrAdd
import com.victorb.lingua.infrastructure.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeckRepositoryImpl @Inject constructor(
    private val localDeckDataSource: LocalDeckDataSource,
    private val localDeckCardDataSource: LocalDeckCardDataSource,
    private val myCardRepository: MyCardRepository
) : DeckRepository, DeckCardRepository, MyDeckRepository, PracticeRepository {

    private val decks = MutableStateFlow(emptyList<Deck>())
    private val unownedCards = MutableStateFlow(emptyList<DeckCard>())

    private val cards: Flow<List<DeckCard>>
        get() = combine(decks, unownedCards) { decks, cards ->
            decks.flatMap { it.cards } + cards
        }

    override suspend fun getCard(id: String): DeckCard? {
        return localDeckCardDataSource.getById(id)?.also {
            Logger.d("Fetched card $id")
        }
    }

    override fun observeCards(deckId: String): Flow<List<DeckCard>> {
        return localDeckCardDataSource.observeByDeckId(deckId)
    }

    override suspend fun saveCard(card: SaveDeckCardData): DeckCard {
        val entity = DeckCard(
            id = card.cardId ?: UUID.randomUUID().toString(),
            deckId = card.deckId,
            input = card.input,
            outputs = card.outputs,
        )

        val deck = localDeckDataSource.getById(entity.deckId)

        if (deck != null) {
            localDeckCardDataSource.insert(entity)
            Logger.d("Added card to deck | $entity")
        } else {
            Logger.d("Added card to unowned list | $entity")
            unownedCards.value = unownedCards.value + entity
        }

        return entity
    }

    override suspend fun getDeck(id: String): Deck? {
        return localDeckDataSource.getById(id).also { Logger.d("Fetched deck $id") }
    }

    override suspend fun getDecks(): List<Deck> {
        return localDeckDataSource.getAll().also { Logger.d("Fetched ${it.size} decks") }
    }

    override suspend fun saveDeck(deck: SaveDeckData): Deck {
        decks.value.find { it.id == deck.deckId }?.let { existingDeck ->

            val orderedCards = existingDeck.cards.sortedBy { card ->
                deck.cards.find { card.id == it.cardId }?.position
            }

            val updatedDeck = existingDeck.copy(
                title = deck.title,
                cards = orderedCards
            )

            decks.value = decks.value.replaceOrAdd({ it.id == existingDeck.id }, updatedDeck)
            Logger.d("Updated deck | $updatedDeck")

            return updatedDeck
        } ?: run {
            val cards = unownedCards.value.filter { it.deckId == deck.deckId }

            val entity = Deck(
                id = deck.deckId,
                title = deck.title,
                cards = cards
            )

            decks.value = decks.value + entity
            unownedCards.value = unownedCards.value - cards.toSet()
            Logger.d("Saved new deck | $entity")
            Logger.d("Removed ${cards.size} cards from unowned cards")

            return entity
        }
    }

    override fun observeMyDecks(): Flow<List<MyDeck>> {
        return combine(decks, myCardRepository.observeMyCards()) { decks, myCards ->
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

    override suspend fun getSession(deckId: String): PracticeSession? {
        val deck = localDeckDataSource.getById(deckId) ?: return null
        return PracticeSessionCreator.create(deck, myCardRepository.getMyCards())
            ?.also { practice ->
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