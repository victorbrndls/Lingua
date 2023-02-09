package com.victorb.lingua.data.mycard.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.victorb.lingua.data.mycard.repository.local.data.MyCardData

@Dao
interface MyCardDao {
    @Query("SELECT * FROM my_card WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): MyCardData?

    @Query("SELECT * FROM my_card WHERE card_id = :deckId LIMIT 1")
    suspend fun getByCardId(deckId: String): MyCardData?

    @Insert
    suspend fun insert(myCardData: MyCardData)
}
