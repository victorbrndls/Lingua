package com.victorb.lingua.core.deck.usecase

import com.victorb.lingua.core.deck.entity.Deck
import com.victorb.lingua.core.deck.repository.DeckRepository
import javax.inject.Inject

interface GetDecksUseCase {

    suspend fun get(): List<Deck>

}

class GetDecksUseCaseImpl @Inject constructor(
    private val deckRepository: DeckRepository
) : GetDecksUseCase {

    override suspend fun get(): List<Deck> {
        return deckRepository.getDecks()
    }

}