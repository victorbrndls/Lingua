package com.victorb.lingua.data.deck.repository

import com.victorb.lingua.core.card.dto.SaveDeckCardData
import com.victorb.lingua.core.card.entity.DeckCard
import com.victorb.lingua.core.card.repository.DeckCardRepository
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

    private val decks = MutableStateFlow(emptyList<Deck>())
    private val unownedCards = MutableStateFlow(emptyList<DeckCard>())

    private val cards: Flow<List<DeckCard>>
        get() = combine(decks, unownedCards) { decks, cards ->
            decks.flatMap { it.cards } + cards
        }

    override suspend fun getCard(id: String): DeckCard? {
        return cards.last().find { it.id == id }
    }

    override fun observeCards(deckId: String): Flow<List<DeckCard>> {
        return cards.map { cards ->
            cards.filter { card -> card.deckId == deckId }
        }
    }

    override suspend fun saveCard(card: SaveDeckCardData): DeckCard {
        val entity = DeckCard(
            card.cardId ?: UUID.randomUUID().toString(),
            card.deckId,
            card.input,
            card.outputs,
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
        return decks.value.find { it.id == id }
    }

}