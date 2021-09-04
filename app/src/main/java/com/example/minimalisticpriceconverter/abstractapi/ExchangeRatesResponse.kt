package com.example.minimalisticpriceconverter.abstractapi

data class ExchangeRatesResponse(
    val base: String,
    val last_updated: Int,
    val exchange_rates: Map<String, Double>
)
