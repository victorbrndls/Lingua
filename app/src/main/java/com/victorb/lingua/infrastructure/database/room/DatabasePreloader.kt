package com.victorb.lingua.infrastructure.database.room

import com.victorb.lingua.data.deck.repository.fakeDecks
import com.victorb.lingua.data.deck.repository.local.dao.DeckCardDao
import com.victorb.lingua.data.deck.repository.local.dao.DeckDao
import com.victorb.lingua.data.deck.repository.local.mapper.DeckCardDataMapper
import com.victorb.lingua.data.deck.repository.local.mapper.DeckDataMapper
import com.victorb.lingua.di.IODispatcher
import com.victorb.lingua.infrastructure.logger.Logger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class DatabasePreloader @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher,
    private val deckDao: DeckDao,
    private val deckCardDao: DeckCardDao,
    private val deckDataMapper: DeckDataMapper,
    private val deckCardDataMapper: DeckCardDataMapper,
) {

    private val scope = CoroutineScope(dispatcher)

    fun preload() {
        scope.launch {
            // Only populate db if it's empty
            if (deckDao.getAll().isNotEmpty()) return@launch

            fakeDecks.forEach { deck ->
                val deckData = deckDataMapper.toData(deck)
                deckDao.insert(deckData)

                deck.cards.forEach { card ->
                    val cardData = deckCardDataMapper.toData(card)
                    deckCardDao.insert(cardData)
                }
            }

            Logger.d("Populated database with fake data")
        }
    }

}