package com.victorb.lingua.data.mycard.repository

import com.victorb.lingua.core.mycard.entity.MyCard
import com.victorb.lingua.core.mycard.repository.MyCardRepository
import com.victorb.lingua.data.mycard.repository.local.LocalMyCardDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MyCardRepositoryImpl @Inject constructor(
    private val localMyCardDataSource: LocalMyCardDataSource
) : MyCardRepository {

    override suspend fun getById(id: String): MyCard? {
        return localMyCardDataSource.getById(id)
    }

    override suspend fun getByCardId(cardId: String): MyCard? {
        return localMyCardDataSource.getByCardId(cardId)
    }

    override suspend fun getMyCards(): List<MyCard> {
        TODO("Not yet implemented")
    }

    override fun observeMyCards(): Flow<List<MyCard>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveMyCard(myCard: MyCard): MyCard {
        localMyCardDataSource.insert(myCard)
        return myCard
    }
}