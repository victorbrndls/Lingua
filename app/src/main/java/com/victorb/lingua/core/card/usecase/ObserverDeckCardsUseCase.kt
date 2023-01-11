package com.victorb.lingua.core.card.usecase

import com.victorb.lingua.core.card.entity.DeckCard
import com.victorb.lingua.core.card.repository.DeckCardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ObserverDeckCardsUseCase {

    fun observe(deckId: String): Flow<List<DeckCard>>

}

class ObserverDeckCardsUseCaseImpl @Inject constructor(
    private val repository: DeckCardRepository,
) : ObserverDeckCardsUseCase {

    override fun observe(deckId: String): Flow<List<DeckCard>> {
        return repository.observeCards(deckId = deckId)
    }

}