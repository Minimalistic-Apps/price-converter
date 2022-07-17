package com.minimalisticapps.priceconverter.data.repository.priceconverter

import com.minimalisticapps.priceconverter.room.entities.FiatCoinExchange
import javax.inject.Inject

class DeleteUseCase @Inject constructor(
    private val priceConverterRepository: PriceConverterRepository
) {
   suspend operator fun invoke(fiatCoinExchange: FiatCoinExchange) = priceConverterRepository.deleteFiatCoin(fiatCoinExchange)
}
