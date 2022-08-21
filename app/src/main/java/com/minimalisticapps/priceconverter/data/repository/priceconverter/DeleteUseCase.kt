package com.minimalisticapps.priceconverter.data.repository.priceconverter

import com.minimalisticapps.priceconverter.room.entities.ScreenCurrencyRecord
import javax.inject.Inject

class DeleteUseCase @Inject constructor(
    private val priceConverterRepository: PriceConverterRepository
) {
   suspend operator fun invoke(screenCurrencyRecord: ScreenCurrencyRecord) = priceConverterRepository.deleteFiatCoin(screenCurrencyRecord)
}
