package com.minimalisticapps.priceconverter.data.repository.priceconverter

import com.minimalisticapps.priceconverter.room.entities.ShitcoinOnScreen
import javax.inject.Inject

class DeleteUseCase @Inject constructor(
    private val priceConverterRepository: PriceConverterRepository
) {
   suspend operator fun invoke(shitcoinOnScreen: ShitcoinOnScreen) = priceConverterRepository.deleteFiatCoin(shitcoinOnScreen)
}
