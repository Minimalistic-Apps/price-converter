package com.minimalisticapps.priceconverter.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.minimalisticapps.priceconverter.data.remote.dto.BitPayExchangeRate
import com.minimalisticapps.priceconverter.room.dao.PriceConverterDao
import com.minimalisticapps.priceconverter.room.entities.FiatCoinExchange

@Database(entities = [BitPayExchangeRate::class, FiatCoinExchange::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun priceConverterDao(): PriceConverterDao
}
