package com.victorb.lingua.data.mycard.repository.local

import com.victorb.lingua.core.mycard.entity.MyCard
import com.victorb.lingua.data.mycard.repository.local.dao.MyCardDao
import com.victorb.lingua.data.mycard.repository.local.mapper.MyCardDataMapper
import javax.inject.Inject

class LocalMyCardDataSource @Inject constructor(
    private val myCardDao: MyCardDao,
    private val myCardDataMapper: MyCardDataMapper
) {

    suspend fun getById(id: String): MyCard? {
        return myCardDao.getById(id)?.let { myCardData ->
            myCardDataMapper.fromData(myCardData)
        }
    }

    suspend fun getByCardId(cardId: String): MyCard? {
        return myCardDao.getByCardId(cardId)?.let { myCardData ->
            myCardDataMapper.fromData(myCardData)
        }
    }

    suspend fun insert(card: MyCard) {
        val data = myCardDataMapper.toData(card)
        myCardDao.insert(data)
    }

}