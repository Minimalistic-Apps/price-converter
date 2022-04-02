package com.minimalisticapps.priceconverter.domain.repo

import com.minimalisticapps.priceconverter.data.remote.dto.BitPayExchangeRate
import com.minimalisticapps.priceconverter.data.remote.dto.BitPayExchangeRatesResponse
import com.minimalisticapps.priceconverter.room.entities.BitPayCoinWithFiatCoin
import com.minimalisticapps.priceconverter.room.entities.FiatCoinExchange
import kotlinx.coroutines.flow.Flow

interface PriceConverterRepository {
    suspend fun getExchangeRates(): BitPayExchangeRatesResponse
    suspend fun getCoins(): Flow<List<BitPayExchangeRate>>
    suspend fun getFiatCoins(): Flow<List<BitPayCoinWithFiatCoin>>
    suspend fun saveFiatCoin(fiatCoinExchange: FiatCoinExchange)
    suspend fun saveCoin(bitPayExchangeRate: BitPayExchangeRate)
    suspend fun deleteFiatCoin(fiatCoinExchange: FiatCoinExchange)
}