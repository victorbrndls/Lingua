package com.victorb.lingua.data.deck.repository

import com.victorb.lingua.core.card.dto.SaveDeckCardData
import com.victorb.lingua.core.card.entity.DeckCard
import com.victorb.lingua.core.card.repository.DeckCardRepository
import com.victorb.lingua.core.deck.dto.SaveDeckData
import com.victorb.lingua.core.deck.entity.Deck
import com.victorb.lingua.core.deck.repository.DeckRepository
import com.victorb.lingua.infrastructure.ktx.replaceOrAdd
import com.victorb.lingua.infrastructure.logger.Logger
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeckRepositoryImpl @Inject constructor() : DeckRepository, DeckCardRepository {

    private val decks = MutableStateFlow(fakeDecks)
    private val unownedCards = MutableStateFlow(emptyList<DeckCard>())

    private val cards: Flow<List<DeckCard>>
        get() = combine(decks, unownedCards) { decks, cards ->
            decks.flatMap { it.cards } + cards
        }

    override suspend fun getCard(id: String): DeckCard? {
        return cards.last().find { it.id == id }?.also { Logger.d("Fetched card $id") }
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

}

private val fakeDecks = listOf(
    Deck(
        id = "deck1",
        title = "10 Italian Words",
        cards = listOf(
            DeckCard(
                id = UUID.randomUUID().toString(),
                deckId = "deck1",
                input = "Lorem ipsum dolor sit amet",
                outputs = listOf("Consectetur adipiscing elit"),
            ),
            DeckCard(
                id = UUID.randomUUID().toString(),
                deckId = "deck1",
                input = "Lorem ipsum?",
                outputs = listOf("Adipiscing elit?"),
            ),
            DeckCard(
                id = UUID.randomUUID().toString(),
                deckId = "deck1",
                input = "Excepteur",
                outputs = listOf("Duis"),
            ),
        )
    ),
    Deck(
        id = "deck2",
        title = "Beginner Words for Portuguese Learners",
        cards = listOf(
            DeckCard(
                id = UUID.randomUUID().toString(),
                deckId = "deck2",
                input = "Oi",
                outputs = listOf("Hello"),
            ),
            DeckCard(
                id = UUID.randomUUID().toString(),
                deckId = "deck2",
                input = "Bom dia",
                outputs = listOf("Good morning"),
            ),
            DeckCard(
                id = UUID.randomUUID().toString(),
                deckId = "deck2",
                input = "Tchau",
                outputs = listOf("Bye"),
            ),
        )
    ),
)