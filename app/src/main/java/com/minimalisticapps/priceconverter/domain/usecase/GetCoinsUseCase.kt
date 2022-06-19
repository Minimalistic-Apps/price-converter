package com.minimalisticapps.priceconverter.domain.usecase

import android.util.Log
import com.minimalisticapps.priceconverter.common.Resource
import com.minimalisticapps.priceconverter.common.utils.formatBtc
import com.minimalisticapps.priceconverter.common.utils.to8Decimal
import com.minimalisticapps.priceconverter.data.remote.dto.BitPayExchangeRate
import com.minimalisticapps.priceconverter.domain.repo.PriceConverterRepository
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

val EIGHT_DECIMAL_PLACES = BigDecimal("0.00000001")
val SIXTEEN_DECIMAL_PLACES = BigDecimal("0.000000000000001")

class GetCoinsUseCase @Inject
constructor(
    private val repository: PriceConverterRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<Resource<Flow<List<BitPayExchangeRate>>>> =
        flow {
            emit(Resource.Loading<Flow<List<BitPayExchangeRate>>>())
            val entries = repository.getExchangeRates().rates.entries
            entries.forEach {
                it.value.code = it.key.uppercase()
                val rate = it.value.rate
                if (rate != null) {
                    val oneShitCoinValue =
                        BigDecimal.ONE.divide(rate, PRECISION, RoundingMode.HALF_UP)
                    it.value.oneShitCoinValue = oneShitCoinValue
                    it.value.oneShitCoinValueString = when {
                        oneShitCoinValue >= EIGHT_DECIMAL_PLACES -> formatBtc(oneShitCoinValue)
                        oneShitCoinValue >= SIXTEEN_DECIMAL_PLACES -> oneShitCoinValue.toString()
                            .to8Decimal()
                        else -> {
                            it.value.oneShitCoinValue = BigDecimal.ZERO
                            it.value.rate = BigDecimal.ZERO
                            "0"
                        }
                    }
                } else {
                    it.value.oneShitCoinValue = null
                    it.value.oneShitCoinValueString = "n/a"
                }
                repository.saveCoin(it.value)
            }
            emit(Resource.Success<Flow<List<BitPayExchangeRate>>>(repository.getCoins()))
        }
            .catch {
                Log.e("GetCoinsUseCase", it.toString() + "\n" + it.stackTraceToString())
                emit(
                    Resource.Error<Flow<List<BitPayExchangeRate>>>(
                        it.localizedMessage
                            ?: "Something went wrong"
                    )
                )
            }
            .flowOn(Dispatchers.IO)

}
