package com.minimalisticapps.priceconverter.room.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.minimalisticapps.priceconverter.data.repository.priceconverter.Shitcoin

data class ShitcoinOnScreenWithRate(
    @Embedded
    val shitcoinOnScreen: ShitcoinOnScreen,

    @Relation(
        parentColumn = "code",
        entity = Shitcoin::class,
        entityColumn = "code"
    )
    val shitcoin: Shitcoin
)
