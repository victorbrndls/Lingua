package com.victorb.lingua.core.deck.usecase

import com.victorb.lingua.core.deck.entity.Deck
import com.victorb.lingua.core.deck.repository.DeckRepository
import javax.inject.Inject

interface GetDeckUseCase {

    suspend fun get(deckId: String): Deck?

}

class GetDeckUseCaseImpl @Inject constructor(
    private val deckRepository: DeckRepository
) : GetDeckUseCase {

    override suspend fun get(deckId: String): Deck? {
        return deckRepository.getDeck(deckId)
    }

}