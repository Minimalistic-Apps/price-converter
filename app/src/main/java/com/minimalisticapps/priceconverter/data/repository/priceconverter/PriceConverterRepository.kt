package com.minimalisticapps.priceconverter.data.repository.priceconverter

import com.minimalisticapps.priceconverter.common.utils.ALLOWED_ISO_CURRENCIES
import com.minimalisticapps.priceconverter.common.utils.AppConstants
import com.minimalisticapps.priceconverter.data.remote.bitpay.BitpayApiInterface
import com.minimalisticapps.priceconverter.data.remote.coingecko.CoingeckoApiInterface
import com.minimalisticapps.priceconverter.room.dao.PriceConverterDao
import com.minimalisticapps.priceconverter.room.entities.ShitcoinOnScreen
import com.minimalisticapps.priceconverter.room.entities.ShitcoinOnScreenWithRate
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject


fun calculateSatsPerUnitRate(rate: BigDecimal?): BigDecimal? {
    if (rate == null) return null

    return BigDecimal.ONE.divide(rate, AppConstants.STORAGE_PRECISION, RoundingMode.HALF_UP)
}

class PriceConverterRepository @Inject constructor(
    private val coingeckoAPi: CoingeckoApiInterface,
    private val bitPayApi: BitpayApiInterface,
    private val priceConverterDao: PriceConverterDao
) {

    suspend fun fetchShitcoinFromApi(): List<Shitcoin> {
        val coingecko: Map<String, Shitcoin> =
            coingeckoAPi.getExchangeRates().rates
                .filter { it.value.type == "fiat" }
                .entries.associate {
                    it.key.uppercase() to Shitcoin(
                        code = it.key.uppercase(),
                        name = it.value.name,
                        satsPerUnit = calculateSatsPerUnitRate(it.value.value),
                    )
                }

        val bitpay: Map<String, Shitcoin> =
            bitPayApi.getExchangeRates().data.associate {
                it.code.uppercase() to Shitcoin(
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

                Shitcoin(
                    code = it.value.first().code,
                    name = it.value.first().name,
                    satsPerUnit = average
                )
            }

        return groupedRates.values.toList()
    }

    fun getShitcoinsFromDatabase(): Flow<List<Shitcoin>> =
        priceConverterDao.fetchShitcoins()

    fun getShitcoinsOnScreenWithRate(): Flow<List<ShitcoinOnScreenWithRate>> =
        priceConverterDao.fetchAllShitcoinOnScreen()

    suspend fun saveFiatCoin(shitcoinOnScreen: ShitcoinOnScreen) {
        priceConverterDao.insertShitcoinOnScreen(shitcoinOnScreen)
    }

    fun updateFiatCoin(shitcoinOnScreen: ShitcoinOnScreen) {
        priceConverterDao.updateShitcoinOnScreen(shitcoinOnScreen)
    }

    suspend fun saveCoin(shitcoin: Shitcoin) {
        priceConverterDao.insertShitcoin(shitcoin)
    }

    suspend fun deleteFiatCoin(shitcoinOnScreen: ShitcoinOnScreen) =
        priceConverterDao.deleteShitcoinOnScreen(shitcoinOnScreen)
}
