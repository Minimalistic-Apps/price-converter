package com.codesses.priceconverter.data.remote.dto

import kotlinx.coroutines.flow.Flow

data class BitPayExchangeRatesResponse(
    val rates: Map<String, BitPayExchangeRate>
)