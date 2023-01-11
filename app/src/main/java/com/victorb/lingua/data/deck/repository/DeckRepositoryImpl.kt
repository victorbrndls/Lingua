package com.victorb.lingua.data.deck.repository

import com.victorb.lingua.core.card.dto.SaveDeckCardData
import com.victorb.lingua.core.card.entity.DeckCard
import com.victorb.lingua.core.card.repository.DeckCardRepository
import com.victorb.lingua.core.deck.entity.Deck
import com.victorb.lingua.core.deck.repository.DeckRepository
import com.victorb.lingua.infrastructure.logger.Logger
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeckRepositoryImpl @Inject constructor() : DeckRepository, DeckCardRepository {

    private val decks = mutableListOf<Deck>()
    private val unownedCards = mutableListOf<DeckCard>()

    private val cards: List<DeckCard>
        get() = decks.flatMap { it.cards } + unownedCards

    override suspend fun get(id: String): DeckCard? {
        return cards.find { it.id == id }
    }

    override suspend fun save(card: SaveDeckCardData): DeckCard {
        val entity = DeckCard(
            card.cardId ?: UUID.randomUUID().toString(),
            card.deckId,
            card.input,
            card.outputs,
        )

        decks.find { it.id == entity.deckId }?.let { deck ->
            val existingCardIdx = deck.cards.indexOfFirst { it.id == entity.id }

            val updatedCards = if (existingCardIdx != -1) {
                deck.cards.mapIndexed { idx, deckCard ->
                    if (existingCardIdx == idx) entity else deckCard
                }
            } else deck.cards + entity

            val updatedDeck = deck.copy(cards = updatedCards)

            decks.remove(deck)
            Logger.d("Added card to deck | $entity")
            decks.add(updatedDeck)
        } ?: run {
            Logger.d("Added card to unowned list | $entity")
            unownedCards.add(entity)
        }

        return entity
    }

    override suspend fun getDeck(id: String): Deck? {
        return decks.find { it.id == id }
    }

}