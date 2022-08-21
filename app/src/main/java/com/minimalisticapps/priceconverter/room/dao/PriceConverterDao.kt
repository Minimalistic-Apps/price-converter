package com.minimalisticapps.priceconverter.room.dao

import androidx.room.*
import com.minimalisticapps.priceconverter.data.repository.priceconverter.CurrencyRate
import com.minimalisticapps.priceconverter.room.entities.ScreenCurrencyRecord
import com.minimalisticapps.priceconverter.room.entities.ScreenCurrencyRecordWithRate
import kotlinx.coroutines.flow.Flow

@Dao
interface PriceConverterDao {
    @Query("SELECT * FROM currency_rate ORDER BY name")
    fun fetchAllCurrencyRates(): Flow<List<CurrencyRate>>

    @Transaction
    @Query("SELECT * FROM screen_currency_record")
    fun fetchAllScreenCurrencyRecords(): Flow<List<ScreenCurrencyRecordWithRate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencyRate(coin: CurrencyRate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScreenCurrencyRecord(coin: ScreenCurrencyRecord)

    @Delete
    suspend fun deleteScreenCurrencyRecord(screenCurrencyRecord: ScreenCurrencyRecord)

    @Update
    fun updateScreenCurrencyRecord(screenCurrencyRecord: ScreenCurrencyRecord)
}
