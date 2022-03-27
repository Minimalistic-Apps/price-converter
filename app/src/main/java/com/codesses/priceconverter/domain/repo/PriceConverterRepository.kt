package com.codesses.priceconverter.domain.repo

import com.codesses.priceconverter.data.remote.dto.BitPayExchangeRatesResponse
import kotlinx.coroutines.flow.Flow

interface PriceConverterRepository {
    suspend fun getExchangeRates(): BitPayExchangeRatesResponse
}