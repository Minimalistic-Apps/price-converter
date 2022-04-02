package com.minimalisticapps.priceconverter.room.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.minimalisticapps.priceconverter.data.remote.dto.BitPayExchangeRate

data class BitPayCoinWithFiatCoin(
    @Embedded
    val fiatCoinExchange: FiatCoinExchange,

    @Relation(
        parentColumn = "unit",
        entity = BitPayExchangeRate::class,
        entityColumn = "unit"
    )
    val bitPayExchangeRate: BitPayExchangeRate,

)