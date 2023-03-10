package com.victorb.lingua.data.deck.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.victorb.lingua.data.deck.repository.local.data.DeckCardOutputData

@Dao
interface DeckCardOutputDao {
    @Query("SELECT * FROM deck_card_output WHERE deck_card_id = :cardId")
    suspend fun getByCardId(cardId: String): List<DeckCardOutputData>

    @Query("SELECT * FROM deck_card_output WHERE deck_id = :deckId")
    suspend fun getByDeckId(deckId: String): List<DeckCardOutputData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(deckCardData: DeckCardOutputData)
}
