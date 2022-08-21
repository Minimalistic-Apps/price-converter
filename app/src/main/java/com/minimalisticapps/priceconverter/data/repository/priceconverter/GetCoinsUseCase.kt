package com.minimalisticapps.priceconverter.data.repository.priceconverter

import android.util.Log
import com.minimalisticapps.priceconverter.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCoinsUseCase @Inject
constructor(
    private val repository: PriceConverterRepository,
) {
    operator fun invoke(): Flow<Resource<Flow<List<ExchangeRate>>>> =
        flow {
            emit(Resource.Loading())
            val entries = repository.getExchangeRates()
            entries.forEach {
                repository.saveCoin(it)
            }
            emit(Resource.Success(repository.getCoins()))
        }
            .catch {
                Log.e("GetCoinsUseCase", it.toString() + "\n" + it.stackTraceToString())
                emit(Resource.Error(it.localizedMessage ?: "Something went wrong"))
            }
            .flowOn(Dispatchers.IO)

}
