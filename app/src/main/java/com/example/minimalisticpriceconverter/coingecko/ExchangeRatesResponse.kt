package com.example.minimalisticpriceconverter.coingecko

data class ExchangeRates(
    val value: Double
)


data class ExchangeRatesResponse(
    val rates: Map<String, ExchangeRates>
)
