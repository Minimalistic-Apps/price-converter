package com.minimalisticapps.priceconverter.domain.usecase

import com.minimalisticapps.priceconverter.domain.repo.PriceConverterRepository
import com.minimalisticapps.priceconverter.room.entities.FiatCoinExchange
import javax.inject.Inject

class DeleteUseCase @Inject constructor(
    private val priceConverterRepository: PriceConverterRepository
) {
   suspend operator fun invoke(fiatCoinExchange: FiatCoinExchange) = priceConverterRepository.deleteFiatCoin(fiatCoinExchange)
}