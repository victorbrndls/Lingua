package com.victorb.lingua.data.deck.repository.local.mapper

import com.victorb.lingua.data.deck.repository.local.data.DeckCardOutputData
import com.victorb.lingua.data.deck.repository.local.dto.DeckCardOutput
import java.util.*
import javax.inject.Inject

class DeckCardOutputDataMapper @Inject constructor() {

    fun toData(output: DeckCardOutput): DeckCardOutputData {
        return DeckCardOutputData(
            id = output.id,
            deckId = output.deckId,
            cardId = output.id,
            output = output.output
        )
    }

    fun fromData(output: DeckCardOutputData): DeckCardOutput {
        return DeckCardOutput(
            id = output.id,
            deckId = output.deckId,
            cardId = output.cardId,
            output = output.output
        )
    }

}