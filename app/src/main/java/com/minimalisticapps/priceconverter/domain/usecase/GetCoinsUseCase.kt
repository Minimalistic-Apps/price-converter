package com.minimalisticapps.priceconverter.domain.usecase

import android.util.Log
import com.minimalisticapps.priceconverter.common.Resource
import com.minimalisticapps.priceconverter.common.utils.formatBtc
import com.minimalisticapps.priceconverter.data.remote.dto.BitPayExchangeRate
import com.minimalisticapps.priceconverter.domain.repo.PriceConverterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCoinsUseCase @Inject
constructor(
    private val repository: PriceConverterRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(bool: Boolean): Flow<Resource<Flow<List<BitPayExchangeRate>>>> =
        flow {
            emit(Resource.Loading<Flow<List<BitPayExchangeRate>>>())
            if (!bool) {
                val entries = repository.getExchangeRates().rates.entries
                entries.forEach {
                    Log.e("SIZE", entries.size.toString())
                    it.value.code = it.key.uppercase()
                    it.value.oneShitCoinValue = 1.div(it.value.rate)
                    it.value.oneShitCoinValueString = formatBtc(it.value.oneShitCoinValue.toBigDecimal())
                    repository.saveCoin(it.value)
                }
            }
            emit(Resource.Success<Flow<List<BitPayExchangeRate>>>(repository.getCoins()))
        }
            .catch {
                emit(Resource.Error<Flow<List<BitPayExchangeRate>>>(it.localizedMessage
                    ?: "Something went wrong"))
            }
            .flowOn(Dispatchers.IO)

}