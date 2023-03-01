package com.victorb.lingua.data.deck.repository.local

import com.victorb.lingua.core.card.entity.DeckCard
import com.victorb.lingua.data.deck.repository.local.dao.DeckCardDao
import com.victorb.lingua.data.deck.repository.local.dao.DeckCardOutputDao
import com.victorb.lingua.data.deck.repository.local.mapper.DeckCardDataMapper
import com.victorb.lingua.data.deck.repository.local.mapper.DeckCardOutputDataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDeckCardDataSource @Inject constructor(
    private val deckCardDao: DeckCardDao,
    private val deckCardOutputDao: DeckCardOutputDao,
    private val deckCardDataMapper: DeckCardDataMapper,
    private val deckCardOutputDataMapper: DeckCardOutputDataMapper
) {

    suspend fun getById(id: String): DeckCard? {
        return deckCardDao.getById(id)?.let { deckCardData ->
            val outputs = deckCardOutputDao.getByCardId(id).let { deckCardOutputsData ->
                deckCardOutputsData.map { deckCardOutputDataMapper.fromData(it) }
            }

            deckCardDataMapper.fromData(deckCardData, outputs)
        }
    }

    suspend fun getByDeckId(deckId: String): List<DeckCard> {
        return deckCardDao.getByDeckId(deckId).let { deckCardsData ->
            val outputs = deckCardOutputDao.getByDeckId(deckId).let { deckCardOutputsData ->
                deckCardOutputsData.map { deckCardOutputDataMapper.fromData(it) }
            }

            deckCardsData.mapNotNull { deckCardData ->
                deckCardDataMapper.fromData(
                    card = deckCardData,
                    outputs = outputs.filter { it.cardId == deckCardData.id }
                )
            }
        }
    }

    fun observeByDeckId(deckId: String): Flow<List<DeckCard>> {
        return deckCardDao.observeByDeckId(deckId).map { deckCardsData ->
            val outputs = deckCardOutputDao.getByDeckId(deckId).let { deckCardOutputsData ->
                deckCardOutputsData.map { deckCardOutputDataMapper.fromData(it) }
            }

            deckCardsData.mapNotNull { deckCardData ->
                deckCardDataMapper.fromData(
                    card = deckCardData,
                    outputs = outputs.filter { it.cardId == deckCardData.id }
                )
            }
        }
    }

    suspend fun insert(card: DeckCard) {
        val data = deckCardDataMapper.toData(card)
        deckCardDao.insert(data)
    }

}