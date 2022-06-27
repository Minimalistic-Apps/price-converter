package com.minimalisticapps.priceconverter.domain.usecase

import com.minimalisticapps.priceconverter.domain.repo.PriceConverterRepository
import com.minimalisticapps.priceconverter.room.entities.BitPayCoinWithFiatCoin
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFiatCoinsUseCase @Inject constructor(
    private val priceConverterRepository: PriceConverterRepository
) {
    suspend operator fun invoke(): Flow<List<BitPayCoinWithFiatCoin>> = priceConverterRepository.getFiatCoins()
}