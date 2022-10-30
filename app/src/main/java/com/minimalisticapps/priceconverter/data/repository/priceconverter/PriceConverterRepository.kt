package com.minimalisticapps.priceconverter.data.repository.priceconverter

import com.minimalisticapps.priceconverter.common.utils.ALLOWED_ISO_CURRENCIES
import com.minimalisticapps.priceconverter.common.utils.AppConstants
import com.minimalisticapps.priceconverter.data.remote.bitpay.BitpayApiInterface
import com.minimalisticapps.priceconverter.data.remote.blockchaininfo.BlockchainInfoApiInterface
import com.minimalisticapps.priceconverter.data.remote.coingecko.CoingeckoApiInterface
import com.minimalisticapps.priceconverter.room.dao.PriceConverterDao
import com.minimalisticapps.priceconverter.room.entities.Shitcoin
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

val CRYPTO_SHITCOINS_THAT_I_LIKE = listOf<String>() // Haha, its empty 🧌

class PriceConverterRepository @Inject constructor(
    private val coingeckoApi: CoingeckoApiInterface,
    private val bitPayApi: BitpayApiInterface,
    private val blockchainInfoApi: BlockchainInfoApiInterface,
    private val priceConverterDao: PriceConverterDao
) {

    suspend fun fetchShitcoinFromApi(): List<Shitcoin> {
        val blockchainInfo = blockchainInfoApi.getExchangeRates()
            .entries.associate {
                it.key.uppercase() to Shitcoin(
                    code = it.key.uppercase(),
                    name = it.value.symbol,
                    satsPerUnit = calculateSatsPerUnitRate(it.value.last),
                )
            }

        val coingecko: Map<String, Shitcoin> =
            coingeckoApi.getExchangeRates().rates
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

        val sourcesData =
            (blockchainInfo.asSequence() + coingecko.asSequence() + bitpay.asSequence())

//        Log.i("PriceConverterRepo", sourcesData.toList().map { it.key }.sorted().toString())

        val groupedRates = sourcesData
            .filter { it.key in ALLOWED_ISO_CURRENCIES || it.key in CRYPTO_SHITCOINS_THAT_I_LIKE }
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
                    ?.divide(
                        entriesWithRate.size.toBigDecimal(),
                        AppConstants.STORAGE_PRECISION,
                        RoundingMode.HALF_UP
                    )

                val code = it.value.first().code

                Shitcoin(
                    code = code,
                    name = ALLOWED_ISO_CURRENCIES[code]?.name ?: code,
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
