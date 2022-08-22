package com.minimalisticapps.priceconverter.data.remote.coingecko

import java.math.BigDecimal

data class CoingeckoApiRate(
    var unit: String,
    var value: BigDecimal?,
    val name: String,
    val type: String,
)
