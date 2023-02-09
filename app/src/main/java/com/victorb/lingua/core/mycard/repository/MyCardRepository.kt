package com.victorb.lingua.core.mycard.repository

import com.victorb.lingua.core.mycard.entity.MyCard
import kotlinx.coroutines.flow.Flow

interface MyCardRepository {

    suspend fun getById(id: String): MyCard?
    suspend fun getByCardId(cardId: String): MyCard?
    suspend fun getMyCards(): List<MyCard>
    fun observeMyCards(): Flow<List<MyCard>>

    suspend fun saveMyCard(myCard: MyCard): MyCard

}