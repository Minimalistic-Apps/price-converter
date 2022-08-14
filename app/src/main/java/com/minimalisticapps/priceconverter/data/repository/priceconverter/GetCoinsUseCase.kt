package com.minimalisticapps.priceconverter.data.repository.priceconverter

import android.util.Log
import com.minimalisticapps.priceconverter.common.Resource
import com.minimalisticapps.priceconverter.data.remote.coingecko.CoinGeckoExchangeRate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

const val PRECISION = 16

class GetCoinsUseCase @Inject
constructor(
    private val repository: PriceConverterRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<Resource<Flow<List<CoinGeckoExchangeRate>>>> =
        flow {
            emit(Resource.Loading<Flow<List<CoinGeckoExchangeRate>>>())
            val entries = repository.getExchangeRates().rates.entries
            entries.forEach {
                it.value.code = it.key.uppercase()
                val rate = it.value.rate
                if (rate != null) {
                    val oneShitCoinValue =
                        BigDecimal.ONE.divide(rate, PRECISION, RoundingMode.HALF_UP)
                    it.value.oneUnitOfShitcoinInBTC = oneShitCoinValue
                } else {
                    it.value.oneUnitOfShitcoinInBTC = null
                }
                repository.saveCoin(it.value)
            }
            emit(Resource.Success<Flow<List<CoinGeckoExchangeRate>>>(repository.getCoins()))
        }
            .catch {
                Log.e("GetCoinsUseCase", it.toString() + "\n" + it.stackTraceToString())
                emit(
                    Resource.Error<Flow<List<CoinGeckoExchangeRate>>>(
                        it.localizedMessage
                            ?: "Something went wrong"
                    )
                )
            }
            .flowOn(Dispatchers.IO)

}
