package com.victorb.lingua.data.deck.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.victorb.lingua.data.deck.repository.local.data.DeckCardData
import kotlinx.coroutines.flow.Flow

@Dao
interface DeckCardDao {
    @Query("SELECT * FROM deck_card WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): DeckCardData?

    @Query("SELECT * FROM deck_card WHERE deck_id = :deckId")
    suspend fun getByDeckId(deckId: String): List<DeckCardData>

    @Query("SELECT * FROM deck_card WHERE deck_id = :deckId")
    fun observeByDeckId(deckId: String): Flow<List<DeckCardData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(deckCardData: DeckCardData)
}
