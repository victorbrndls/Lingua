package com.victorb.lingua.data.deck.repository.local

import com.victorb.lingua.data.deck.repository.local.dao.DeckCardOutputDao
import com.victorb.lingua.data.deck.repository.local.dto.DeckCardOutput
import com.victorb.lingua.data.deck.repository.local.mapper.DeckCardOutputDataMapper
import javax.inject.Inject

class LocalDeckCardOutputDataSource @Inject constructor(
    private val deckCardOutputDao: DeckCardOutputDao,
    private val mapper: DeckCardOutputDataMapper
) {

    suspend fun getByDeckId(deckId: String): List<DeckCardOutput> {
        return deckCardOutputDao.getByDeckId(deckId).let { deckCardOutputsData ->
            deckCardOutputsData.map { mapper.fromData(it) }
        }
    }

    suspend fun insert(card: DeckCardOutput) {
        val data = mapper.toData(card)
        deckCardOutputDao.insert(data)
    }

}