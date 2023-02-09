package com.victorb.lingua.data.mycard.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.victorb.lingua.data.mycard.repository.local.data.MyCardData
import kotlinx.coroutines.flow.Flow

@Dao
interface MyCardDao {
    @Query("SELECT * FROM my_card")
    suspend fun getAll(): List<MyCardData>

    @Query("SELECT * FROM my_card WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): MyCardData?

    @Query("SELECT * FROM my_card WHERE card_id = :deckId LIMIT 1")
    suspend fun getByCardId(deckId: String): MyCardData?

    @Query("SELECT * FROM my_card")
    fun observeAll(): Flow<List<MyCardData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(myCardData: MyCardData)
}
