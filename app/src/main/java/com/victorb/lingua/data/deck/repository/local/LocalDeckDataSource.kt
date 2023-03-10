package com.victorb.lingua.data.deck.repository.local

import com.victorb.lingua.core.deck.entity.Deck
import com.victorb.lingua.data.deck.repository.local.dao.DeckCardDao
import com.victorb.lingua.data.deck.repository.local.dao.DeckCardOutputDao
import com.victorb.lingua.data.deck.repository.local.dao.DeckDao
import com.victorb.lingua.data.deck.repository.local.mapper.DeckCardOutputDataMapper
import com.victorb.lingua.data.deck.repository.local.mapper.DeckDataMapper
import com.victorb.lingua.infrastructure.ktx.replaceOrAdd
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.scan
import javax.inject.Inject

class LocalDeckDataSource @Inject constructor(
    private val deckDao: DeckDao,
    private val deckCardDao: DeckCardDao,
    private val deckCardOutputDao: DeckCardOutputDao,
    private val deckDataMapper: DeckDataMapper,
    private val deckCardOutputDataMapper: DeckCardOutputDataMapper
) {

    suspend fun getAll(): List<Deck> {
        return deckDao.getAll().mapNotNull { deckData ->
            val cardsData = deckCardDao.getByDeckId(deckData.id)
            val outputs = deckCardOutputDao.getByDeckId(deckData.id).let { outputsData ->
                outputsData.map { deckCardOutputDataMapper.fromData(it) }
            }

            deckDataMapper.fromData(deckData, cardsData, outputs)
        }
    }

    fun observeAll(): Flow<List<Deck>> = flow {
        deckDao.observeAll().collect { decksData ->
            decksData.forEach { deckData ->
                deckCardDao.observeByDeckId(deckData.id).collect { cardsData ->
                    val outputs = deckCardOutputDao.getByDeckId(deckData.id).let { outputsData ->
                        outputsData.map { deckCardOutputDataMapper.fromData(it) }
                    }

                    val deck = deckDataMapper.fromData(deckData, cardsData, outputs)
                    emit(deck)
                }
            }
        }
    }.scan(emptyList()) { decks, deck ->
        deck ?: return@scan decks
        decks.replaceOrAdd({ it.id == deck.id }, deck)
    }

    suspend fun getById(id: String): Deck? {
        return deckDao.getById(id)?.let { deckData ->
            val cardsData = deckCardDao.getByDeckId(id)
            val outputs = deckCardOutputDao.getByDeckId(deckData.id).let { outputsData ->
                outputsData.map { deckCardOutputDataMapper.fromData(it) }
            }

            deckDataMapper.fromData(deckData, cardsData, outputs)
        }
    }

    suspend fun insert(deck: Deck) {
        val data = deckDataMapper.toData(deck)
        deckDao.insert(data)
    }

}