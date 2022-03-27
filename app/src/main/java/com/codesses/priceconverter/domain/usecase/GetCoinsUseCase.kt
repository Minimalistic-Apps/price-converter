package com.codesses.priceconverter.domain.usecase

import com.codesses.priceconverter.common.Resource
import com.codesses.priceconverter.data.remote.dto.BitPayExchangeRate
import com.codesses.priceconverter.domain.repo.PriceConverterRepository
import com.codesses.priceconverter.room.dao.PriceConverterDao
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
    private val priceConverterDao: PriceConverterDao,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(bool: Boolean): Flow<Resource<Flow<List<BitPayExchangeRate>>>> =
        flow {
            emit(Resource.Loading<Flow<List<BitPayExchangeRate>>>())
            if (!bool) {
                repository.getExchangeRates().rates.entries.forEach {
                    priceConverterDao.insertCoin(it.value)
                }
            }
            emit(Resource.Success<Flow<List<BitPayExchangeRate>>>(priceConverterDao.fetchAllCoins()))
        }
            .catch {
                emit(Resource.Error<Flow<List<BitPayExchangeRate>>>(it.localizedMessage
                    ?: "Something went wrong"))
            }
            .flowOn(Dispatchers.IO)

}