package com.minimalisticapps.priceconverter.data.repository.priceconverter

import com.minimalisticapps.priceconverter.room.entities.ScreenCurrencyRecordWithRate
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFiatCoinsUseCase @Inject constructor(
    private val priceConverterRepository: PriceConverterRepository
) {
    suspend operator fun invoke(): Flow<List<ScreenCurrencyRecordWithRate>> = priceConverterRepository.getFiatCoins()
}
