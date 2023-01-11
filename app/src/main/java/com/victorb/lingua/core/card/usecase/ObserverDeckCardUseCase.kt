package com.victorb.lingua.core.card.usecase

import com.victorb.lingua.core.card.entity.DeckCard
import com.victorb.lingua.core.card.repository.DeckCardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ObserverDeckCardUseCase {

    fun observe(deckId: String): Flow<List<DeckCard>>

}

class ObserverDeckCardUseCaseImpl @Inject constructor(
    private val repository: DeckCardRepository,
) : ObserverDeckCardUseCase {

    override fun observe(deckId: String): Flow<List<DeckCard>> {
        return repository.observeCards(deckId = deckId)
    }

}