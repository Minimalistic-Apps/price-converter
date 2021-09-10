package com.example.minimalisticpriceconverter.coingecko

data class CoingeckoExchangeRate(
    val value: Double
)


data class CoingeckoExchangeRatesResponse(
    val rates: Map<String, CoingeckoExchangeRate>
)
