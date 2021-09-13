package com.minimalisticapps.priceconverter.coingecko

data class CoingeckoExchangeRate(
    val value: Double
)


data class CoingeckoExchangeRatesResponse(
    val rates: Map<String, CoingeckoExchangeRate>
)
