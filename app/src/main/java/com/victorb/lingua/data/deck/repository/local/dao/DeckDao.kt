package com.victorb.lingua.data.deck.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.victorb.lingua.data.deck.repository.local.data.DeckData

@Dao
interface DeckDao {
    @Query("SELECT * FROM deck")
    suspend fun getAll(): List<DeckData>

    @Query("SELECT * FROM deck WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): DeckData?

    @Insert
    suspend fun insert(deckData: DeckData)
}
