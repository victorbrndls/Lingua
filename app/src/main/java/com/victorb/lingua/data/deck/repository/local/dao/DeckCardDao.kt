package com.victorb.lingua.data.deck.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.victorb.lingua.data.deck.repository.local.data.DeckCardData

@Dao
interface DeckCardDao {
    @Query("SELECT * FROM deck_card WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): DeckCardData?

    @Query("SELECT * FROM deck_card WHERE deck_id = :id")
    suspend fun getByDeckId(id: String): List<DeckCardData>

    @Insert
    suspend fun insert(deckCardData: DeckCardData)
}
