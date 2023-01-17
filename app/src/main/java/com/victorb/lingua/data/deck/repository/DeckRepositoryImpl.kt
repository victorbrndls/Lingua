package com.victorb.lingua.data.deck.repository

import com.victorb.lingua.core.card.dto.SaveDeckCardData
import com.victorb.lingua.core.card.entity.DeckCard
import com.victorb.lingua.core.card.repository.DeckCardRepository
import com.victorb.lingua.core.deck.dto.SaveDeckData
import com.victorb.lingua.core.deck.entity.Deck
import com.victorb.lingua.core.deck.repository.DeckRepository
import com.victorb.lingua.core.mydeck.entity.MyDeck
import com.victorb.lingua.core.mydeck.repository.MyDeckRepository
import com.victorb.lingua.core.practice.entity.PracticeSession
import com.victorb.lingua.core.practice.repository.PracticeRepository
import com.victorb.lingua.infrastructure.ktx.replaceOrAdd
import com.victorb.lingua.infrastructure.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeckRepositoryImpl @Inject constructor() :
    DeckRepository, DeckCardRepository, MyDeckRepository, PracticeRepository {

    private val decks = MutableStateFlow(fakeDecks)
    private val unownedCards = MutableStateFlow(emptyList<DeckCard>())

    private val cards: Flow<List<DeckCard>>
        get() = combine(decks, unownedCards) { decks, cards ->
            decks.flatMap { it.cards } + cards
        }

    override suspend fun getCard(id: String): DeckCard? {
        return (decks.value.flatMap { it.cards } + unownedCards.value)
            .find { it.id == id }
            ?.also { Logger.d("Fetched card $id") }
    }

    override fun observeCards(deckId: String): Flow<List<DeckCard>> {
        return cards.map { cards ->
            cards.filter { card -> card.deckId == deckId }
        }
    }

    override suspend fun saveCard(card: SaveDeckCardData): DeckCard {
        val entity = DeckCard(
            id = card.cardId ?: UUID.randomUUID().toString(),
            deckId = card.deckId,
            input = card.input,
            outputs = card.outputs,
        )

        decks.value.find { it.id == entity.deckId }?.let { deck ->
            val updatedCards = deck.cards.replaceOrAdd({ it.id == entity.id }, entity)
            val updatedDeck = deck.copy(cards = updatedCards)

            decks.value = decks.value - deck + updatedDeck
            Logger.d("Added card to deck | $entity")
        } ?: run {
            Logger.d("Added card to unowned list | $entity")
            unownedCards.value = unownedCards.value + entity
        }

        return entity
    }

    override suspend fun getDeck(id: String): Deck? {
        return decks.value.find { it.id == id }?.also { Logger.d("Fetched deck $id") }
    }

    override suspend fun getDecks(): List<Deck> {
        return decks.value.also { Logger.d("Fetched ${it.size} decks") }
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
        return decks
            .map { decks ->
                decks.map { deck ->
                    MyDeck(
                        id = deck.id,
                        title = deck.title,
                        learnedCards = 0,
                        totalCards = deck.cards.size,
                    )
                }
            }
    }

    override suspend fun getSession(deckId: String): PracticeSession? {
        return null
    }

}