package com.victorb.lingua.core.deck.repository

import com.victorb.lingua.core.deck.entity.Deck

interface DeckRepository {

    suspend fun getDeck(id: String): Deck?

}