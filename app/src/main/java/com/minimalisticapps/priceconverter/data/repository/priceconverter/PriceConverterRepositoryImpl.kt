package com.minimalisticapps.priceconverter.data.repository.priceconverter

import com.minimalisticapps.priceconverter.common.utils.ALLOWED_ISO_CURRENCIES
import com.minimalisticapps.priceconverter.common.utils.AppConstants
import com.minimalisticapps.priceconverter.data.remote.bitpay.BitpayApiInterface
import com.minimalisticapps.priceconverter.data.remote.coingecko.CoingeckoApiInterface
import com.minimalisticapps.priceconverter.room.dao.PriceConverterDao
import com.minimalisticapps.priceconverter.room.entities.ScreenCurrencyRecord
import com.minimalisticapps.priceconverter.room.entities.ScreenCurrencyRecordWithRate
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

    override suspend fun getExchangeRates(): List<CurrencyRate> {
        val coingecko: Map<String, CurrencyRate> =
            coingeckoAPi.getExchangeRates().rates
                .filter { it.value.type == "fiat" }
                .entries.associate {
                    it.key.uppercase() to CurrencyRate(
                        code = it.key.uppercase(),
                        name = it.value.name,
                        satsPerUnit = calculateSatsPerUnitRate(it.value.value),
                    )
                }

        val bitpay: Map<String, CurrencyRate> =
            bitPayApi.getExchangeRates().data.associate {
                it.code.uppercase() to CurrencyRate(
                    code = it.code.uppercase(),
                    name = it.name,
                    satsPerUnit = calculateSatsPerUnitRate(it.rate),
                )
            }

        val groupedRates = (coingecko.asSequence() + bitpay.asSequence())
            .filter { it.key in ALLOWED_ISO_CURRENCIES }
            .groupBy({ it.key }, { it.value })
            .mapValues {
                val entriesWithRate = it.value.filter { it2 -> it2.satsPerUnit != null }

                val average = entriesWithRate.map { it2 ->
                    it2.satsPerUnit
                }
                    .reduce { acc, rate ->
                        if (acc != null)
                            (if (rate != null) acc.plus(rate) else acc)
                        else BigDecimal.ZERO
                    }
                    ?.divide(entriesWithRate.size.toBigDecimal())

                CurrencyRate(
                    code = it.value.first().code,
                    name = it.value.first().name,
                    satsPerUnit = average
                )
            }

        return groupedRates.values.toList()
    }

    override suspend fun getCoins(): Flow<List<CurrencyRate>> =
        priceConverterDao.fetchAllCurrencyRates()

    override suspend fun getFiatCoins(): Flow<List<ScreenCurrencyRecordWithRate>> =
        priceConverterDao.fetchAllScreenCurrencyRecords()

    override suspend fun saveFiatCoin(screenCurrencyRecord: ScreenCurrencyRecord) {
        priceConverterDao.insertScreenCurrencyRecord(screenCurrencyRecord)
    }

    override suspend fun updateFiatCoin(screenCurrencyRecord: ScreenCurrencyRecord) {
        priceConverterDao.updateScreenCurrencyRecord(screenCurrencyRecord)
    }

    override suspend fun saveCoin(currencyRate: CurrencyRate) {
        priceConverterDao.insertCurrencyRate(currencyRate)
    }

    override suspend fun deleteFiatCoin(screenCurrencyRecord: ScreenCurrencyRecord) =
        priceConverterDao.deleteScreenCurrencyRecord(screenCurrencyRecord)
}
