package com.victorb.lingua.core.mydeck.repository

import com.victorb.lingua.core.mydeck.entity.MyDeck
import kotlinx.coroutines.flow.Flow

interface MyDeckRepository {

    fun observeMyDecks(): Flow<List<MyDeck>>

}