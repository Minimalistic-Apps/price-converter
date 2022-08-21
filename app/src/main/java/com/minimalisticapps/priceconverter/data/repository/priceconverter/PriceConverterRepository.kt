package com.minimalisticapps.priceconverter.data.repository.priceconverter

import com.minimalisticapps.priceconverter.room.entities.ScreenCurrencyRecord
import com.minimalisticapps.priceconverter.room.entities.ScreenCurrencyRecordWithRate
import kotlinx.coroutines.flow.Flow

interface PriceConverterRepository {
    suspend fun getExchangeRates(): List<CurrencyRate>
    suspend fun getCoins(): Flow<List<CurrencyRate>>
    suspend fun getFiatCoins(): Flow<List<ScreenCurrencyRecordWithRate>>
    suspend fun saveFiatCoin(screenCurrencyRecord: ScreenCurrencyRecord)
    suspend fun updateFiatCoin(screenCurrencyRecord: ScreenCurrencyRecord)
    suspend fun saveCoin(currencyRate: CurrencyRate)
    suspend fun deleteFiatCoin(screenCurrencyRecord: ScreenCurrencyRecord)
}
