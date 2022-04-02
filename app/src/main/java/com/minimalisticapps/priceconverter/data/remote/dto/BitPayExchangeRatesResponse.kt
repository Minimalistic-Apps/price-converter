package com.minimalisticapps.priceconverter.data.remote.dto

data class BitPayExchangeRatesResponse(
    val rates: Map<String, BitPayExchangeRate>
)