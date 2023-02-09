package com.victorb.lingua.data.mycard.repository.local.mapper

import com.victorb.lingua.core.mycard.entity.MyCard
import com.victorb.lingua.data.mycard.repository.local.data.MyCardData
import javax.inject.Inject

class MyCardDataMapper @Inject constructor() {

    fun toData(card: MyCard): MyCardData {
        return MyCardData(
            id = card.id,
            cardId = card.cardId,
        )
    }

    fun fromData(card: MyCardData): MyCard? {
        return MyCard(
            id = card.id,
            cardId = card.cardId,
            practices = emptyList()
        )
    }

}