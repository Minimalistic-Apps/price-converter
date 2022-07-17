package com.minimalisticapps.priceconverter.data.repository.priceconverter

import com.minimalisticapps.priceconverter.room.entities.BitPayCoinWithFiatCoin
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFiatCoinsUseCase @Inject constructor(
    private val priceConverterRepository: PriceConverterRepository
) {
    suspend operator fun invoke(): Flow<List<BitPayCoinWithFiatCoin>> = priceConverterRepository.getFiatCoins()
}