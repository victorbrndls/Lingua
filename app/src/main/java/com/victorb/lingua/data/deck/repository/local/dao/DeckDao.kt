package com.victorb.lingua.data.deck.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.victorb.lingua.data.deck.repository.local.data.DeckData
import kotlinx.coroutines.flow.Flow

@Dao
interface DeckDao {
    @Query("SELECT * FROM deck")
    suspend fun getAll(): List<DeckData>

    @Query("SELECT * FROM deck")
    fun observeAll(): Flow<List<DeckData>>

    @Query("SELECT * FROM deck WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): DeckData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(deckData: DeckData)
}
