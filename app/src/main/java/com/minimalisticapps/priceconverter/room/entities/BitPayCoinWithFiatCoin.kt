package com.minimalisticapps.priceconverter.room.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.minimalisticapps.priceconverter.data.remote.coingecko.CoinGeckoExchangeRate

data class BitPayCoinWithFiatCoin(
    @Embedded
    val fiatCoinExchange: FiatCoinExchange,

    @Relation(
        parentColumn = "unit",
        entity = CoinGeckoExchangeRate::class,
        entityColumn = "unit"
    )
    val exchangeRate: CoinGeckoExchangeRate,

    )
