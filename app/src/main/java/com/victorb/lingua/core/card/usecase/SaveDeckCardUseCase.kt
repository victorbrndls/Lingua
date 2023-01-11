package com.victorb.lingua.core.card.usecase

import com.victorb.lingua.core.card.dto.SaveDeckCardData
import com.victorb.lingua.core.card.entity.DeckCard
import com.victorb.lingua.core.card.repository.DeckCardRepository
import javax.inject.Inject

interface SaveDeckCardUseCase {

    suspend fun save(card: SaveDeckCardData): DeckCard

}

class SaveCardUseCaseImpl @Inject constructor(
    private val repository: DeckCardRepository,
) : SaveDeckCardUseCase {

    override suspend fun save(card: SaveDeckCardData): DeckCard {
        return repository.saveCard(card)
    }

}