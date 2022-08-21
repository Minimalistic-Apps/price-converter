package com.minimalisticapps.priceconverter.data.repository.priceconverter

import com.minimalisticapps.priceconverter.common.utils.ALLOWED_ISO_CURRENCIES
import com.minimalisticapps.priceconverter.common.utils.AppConstants
import com.minimalisticapps.priceconverter.data.remote.bitpay.BitpayApiInterface
import com.minimalisticapps.priceconverter.data.remote.coingecko.CoingeckoApiInterface
import com.minimalisticapps.priceconverter.room.dao.PriceConverterDao
import com.minimalisticapps.priceconverter.room.entities.ExchangeRateWithFiatCoin
import com.minimalisticapps.priceconverter.room.entities.FiatCoinExchange
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject


fun calculateSatsPerUnitRate(rate: BigDecimal?): BigDecimal? {
    if (rate == null) return null

    return BigDecimal.ONE.divide(rate, AppConstants.STORAGE_PRECISION, RoundingMode.HALF_UP)
}

class PriceConverterRepositoryImpl @Inject constructor(
    private val coingeckoAPi: CoingeckoApiInterface,
    private val bitPayApi: BitpayApiInterface,
    private val priceConverterDao: PriceConverterDao
) : PriceConverterRepository {

    override suspend fun getExchangeRates(): List<ExchangeRate> {

        val coingecko: Map<String, ExchangeRate> =
            coingeckoAPi.getExchangeRates().rates.entries.associate {
                it.key.uppercase() to ExchangeRate(
                    code = it.key.uppercase(),
                    name = it.value.name,
                    type = it.value.type,
                    source = "coingecko",
                    oneUnitOfShitcoinInBTC = calculateSatsPerUnitRate(it.value.value),
                )
            }

        val bitpay: Map<String, ExchangeRate> =
            bitPayApi.getExchangeRates().data.associate {
                it.code.uppercase() to ExchangeRate(
                    code = it.code.uppercase(),
                    name = it.name,
                    type = "fiat", // Todo: solve this
                    source = "bitpay",
                    oneUnitOfShitcoinInBTC = calculateSatsPerUnitRate(it.rate),
                )
            }

        val groupedRates = (coingecko.asSequence() + bitpay.asSequence())
            .filter { it.key in ALLOWED_ISO_CURRENCIES }
            .groupBy({ it.key }, { it.value })
            .mapValues {
                val entriesWithRate = it.value.filter { it2 -> it2.oneUnitOfShitcoinInBTC != null }

                val average = entriesWithRate.map { it2 ->
                    it2.oneUnitOfShitcoinInBTC
                }
                    .reduce { acc, rate ->
                        if (acc != null)
                            (if (rate != null) acc.plus(rate) else acc)
                        else BigDecimal.ZERO
                    }
                    ?.divide(entriesWithRate.size.toBigDecimal())

                ExchangeRate(
                    code = it.value.first().code,
                    name = it.value.first().name,
                    type = it.value.first().type,
                    source = entriesWithRate.joinToString(",") { it2 -> it2.source },
                    oneUnitOfShitcoinInBTC = average
                )
            }

        return groupedRates.values.toList()
    }

    override suspend fun getCoins(): Flow<List<ExchangeRate>> =
        priceConverterDao.fetchAllCoins()

    override suspend fun getFiatCoins(): Flow<List<ExchangeRateWithFiatCoin>> =
        priceConverterDao.fetchAllFiatCoins()

    override suspend fun saveFiatCoin(fiatCoinExchange: FiatCoinExchange) {
        priceConverterDao.insertFiatCoin(fiatCoinExchange)
    }

    override suspend fun updateFiatCoin(fiatCoinExchange: FiatCoinExchange) {
        priceConverterDao.updateFiatCoin(fiatCoinExchange)
    }

    override suspend fun saveCoin(exchangeRate: ExchangeRate) {
        priceConverterDao.insertCoin(exchangeRate)
    }

    override suspend fun deleteFiatCoin(fiatCoinExchange: FiatCoinExchange) =
        priceConverterDao.deleteFiatCoin(fiatCoinExchange)
}
