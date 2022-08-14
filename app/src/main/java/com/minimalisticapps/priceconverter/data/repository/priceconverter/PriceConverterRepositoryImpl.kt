package com.minimalisticapps.priceconverter.data.repository.priceconverter

import com.minimalisticapps.priceconverter.data.remote.coingecko.CoinGeckoApiInterface
import com.minimalisticapps.priceconverter.data.remote.coingecko.CoinGeckoExchangeRate
import com.minimalisticapps.priceconverter.data.remote.coingecko.CoinGeckoExchangeRatesResponse
import com.minimalisticapps.priceconverter.room.dao.PriceConverterDao
import com.minimalisticapps.priceconverter.room.entities.BitPayCoinWithFiatCoin
import com.minimalisticapps.priceconverter.room.entities.FiatCoinExchange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PriceConverterRepositoryImpl @Inject constructor(
    private val coinGeckoApi: CoinGeckoApiInterface,
    private val priceConverterDao: PriceConverterDao
) : PriceConverterRepository {

    override suspend fun getExchangeRates(): CoinGeckoExchangeRatesResponse =
        coinGeckoApi.getExchangeRates()

    override suspend fun getCoins(): Flow<List<CoinGeckoExchangeRate>> =
        priceConverterDao.fetchAllCoins()

    override suspend fun getFiatCoins(): Flow<List<BitPayCoinWithFiatCoin>> =
        priceConverterDao.fetchAllFiatCoins()

    override suspend fun saveFiatCoin(fiatCoinExchange: FiatCoinExchange) {
        priceConverterDao.insertFiatCoin(fiatCoinExchange)
    }

    override suspend fun updateFiatCoin(fiatCoinExchange: FiatCoinExchange) {
        priceConverterDao.updateFiatCoin(fiatCoinExchange)
    }

    override suspend fun saveCoin(bitPayExchangeRate: CoinGeckoExchangeRate) {
        priceConverterDao.insertCoin(bitPayExchangeRate)
    }

    override suspend fun deleteFiatCoin(fiatCoinExchange: FiatCoinExchange) =
        priceConverterDao.deleteFiatCoin(fiatCoinExchange)
}
