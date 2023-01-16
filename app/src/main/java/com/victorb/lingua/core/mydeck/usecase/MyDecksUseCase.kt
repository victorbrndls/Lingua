package com.victorb.lingua.core.mydeck.usecase

import com.victorb.lingua.core.mydeck.entity.MyDeck
import com.victorb.lingua.core.mydeck.repository.MyDeckRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MyDecksUseCase {

    suspend fun observeMyDecks(): Flow<List<MyDeck>>

}

class MyDecksUseCaseImpl @Inject constructor(
    private val myDeckRepository: MyDeckRepository
) : MyDecksUseCase {

    override suspend fun observeMyDecks(): Flow<List<MyDeck>> {
        return myDeckRepository.observeMyDecks()
    }

}