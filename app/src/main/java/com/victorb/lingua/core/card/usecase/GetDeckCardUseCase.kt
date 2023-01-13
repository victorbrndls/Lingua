package com.victorb.lingua.core.card.usecase

import com.victorb.lingua.core.card.entity.DeckCard
import com.victorb.lingua.core.card.repository.DeckCardRepository
import javax.inject.Inject

interface GetDeckCardUseCase {

    suspend fun get(cardId: String): DeckCard?

}

class GetDeckCardUseCaseImpl @Inject constructor(
    private val repository: DeckCardRepository,
) : GetDeckCardUseCase {

    override suspend fun get(cardId: String): DeckCard? {
        return repository.getCard(cardId)
    }

}