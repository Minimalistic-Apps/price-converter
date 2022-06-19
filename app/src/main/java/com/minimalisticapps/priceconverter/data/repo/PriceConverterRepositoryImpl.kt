package com.minimalisticapps.priceconverter.data.repo

import com.minimalisticapps.priceconverter.data.remote.ApiInterface
import com.minimalisticapps.priceconverter.data.remote.dto.BitPayExchangeRate
import com.minimalisticapps.priceconverter.data.remote.dto.BitPayExchangeRatesResponse
import com.minimalisticapps.priceconverter.domain.repo.PriceConverterRepository
import com.minimalisticapps.priceconverter.room.dao.PriceConverterDao
import com.minimalisticapps.priceconverter.room.entities.BitPayCoinWithFiatCoin
import com.minimalisticapps.priceconverter.room.entities.FiatCoinExchange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PriceConverterRepositoryImpl @Inject constructor(
    private val api: ApiInterface,
    private val priceConverterDao: PriceConverterDao
) : PriceConverterRepository {

    override suspend fun getExchangeRates(): BitPayExchangeRatesResponse = api.get()

    override suspend fun getCoins(): Flow<List<BitPayExchangeRate>> =
        priceConverterDao.fetchAllCoins()

    override suspend fun getFiatCoins(): Flow<List<BitPayCoinWithFiatCoin>> =
        priceConverterDao.fetchAllFiatCoins()

    override suspend fun saveFiatCoin(fiatCoinExchange: FiatCoinExchange) {
        priceConverterDao.insertFiatCoin(fiatCoinExchange)
    }

    override suspend fun updateFiatCoin(fiatCoinExchange: FiatCoinExchange) {
        priceConverterDao.updateFiatCoin(fiatCoinExchange)
    }

    override suspend fun saveCoin(bitPayExchangeRate: BitPayExchangeRate) {
        priceConverterDao.insertCoin(bitPayExchangeRate)
    }

    override suspend fun deleteFiatCoin(fiatCoinExchange: FiatCoinExchange) =
        priceConverterDao.deleteFiatCoin(fiatCoinExchange)
}
