package com.minimalisticapps.priceconverter.data.repository.priceconverter

import com.minimalisticapps.priceconverter.room.entities.ExchangeRateWithFiatCoin
import com.minimalisticapps.priceconverter.room.entities.FiatCoinExchange
import kotlinx.coroutines.flow.Flow

interface PriceConverterRepository {
    suspend fun getExchangeRates(): List<ExchangeRate>
    suspend fun getCoins(): Flow<List<ExchangeRate>>
    suspend fun getFiatCoins(): Flow<List<ExchangeRateWithFiatCoin>>
    suspend fun saveFiatCoin(fiatCoinExchange: FiatCoinExchange)
    suspend fun updateFiatCoin(fiatCoinExchange: FiatCoinExchange)
    suspend fun saveCoin(exchangeRate: ExchangeRate)
    suspend fun deleteFiatCoin(fiatCoinExchange: FiatCoinExchange)
}
