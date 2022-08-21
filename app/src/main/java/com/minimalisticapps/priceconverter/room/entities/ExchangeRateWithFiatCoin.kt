package com.minimalisticapps.priceconverter.room.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.minimalisticapps.priceconverter.data.repository.priceconverter.ExchangeRate

data class ExchangeRateWithFiatCoin(
    @Embedded
    val fiatCoinExchange: FiatCoinExchange,

    @Relation(
        parentColumn = "unit",
        entity = ExchangeRate::class,
        entityColumn = "unit"
    )
    val exchangeRate: ExchangeRate,

    )
