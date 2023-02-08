package com.victorb.lingua.data.deck.repository.local

import com.victorb.lingua.core.card.entity.DeckCard
import com.victorb.lingua.data.deck.repository.local.dao.DeckCardDao
import com.victorb.lingua.data.deck.repository.local.mapper.DeckCardDataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDeckCardDataSource @Inject constructor(
    private val deckCardDao: DeckCardDao,
    private val deckCardDataMapper: DeckCardDataMapper
) {

    suspend fun getById(id: String): DeckCard? {
        return deckCardDao.getById(id)?.let { deckCardData ->
            deckCardDataMapper.fromData(deckCardData)
        }
    }

    suspend fun getByDeckId(deckId: String): List<DeckCard> {
        return deckCardDao.getByDeckId(deckId).let { deckCardsData ->
            deckCardsData.mapNotNull { deckCardDataMapper.fromData(it) }
        }
    }

    fun observeByDeckId(deckId: String): Flow<List<DeckCard>> {
        return deckCardDao.observeByDeckId(deckId).map { deckCardsData ->
            deckCardsData.mapNotNull { deckCardDataMapper.fromData(it) }
        }
    }

    suspend fun insert(card: DeckCard) {
        val data = deckCardDataMapper.toData(card)
        deckCardDao.insert(data)
    }

}