package com.minimalisticapps.priceconverter.data.repository.priceconverter

import com.minimalisticapps.priceconverter.data.remote.coingecko.CoinGeckoExchangeRate
import com.minimalisticapps.priceconverter.data.remote.coingecko.CoinGeckoExchangeRatesResponse
import com.minimalisticapps.priceconverter.room.entities.BitPayCoinWithFiatCoin
import com.minimalisticapps.priceconverter.room.entities.FiatCoinExchange
import kotlinx.coroutines.flow.Flow

interface PriceConverterRepository {
    suspend fun getExchangeRates(): CoinGeckoExchangeRatesResponse
    suspend fun getCoins(): Flow<List<CoinGeckoExchangeRate>>
    suspend fun getFiatCoins(): Flow<List<BitPayCoinWithFiatCoin>>
    suspend fun saveFiatCoin(fiatCoinExchange: FiatCoinExchange)
    suspend fun updateFiatCoin(fiatCoinExchange: FiatCoinExchange)
    suspend fun saveCoin(bitPayExchangeRate: CoinGeckoExchangeRate)
    suspend fun deleteFiatCoin(fiatCoinExchange: FiatCoinExchange)
}
