package com.minimalisticapps.priceconverter.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.minimalisticapps.priceconverter.data.RoomConverter
import com.minimalisticapps.priceconverter.data.remote.coingecko.CoinGeckoExchangeRate
import com.minimalisticapps.priceconverter.room.dao.PriceConverterDao
import com.minimalisticapps.priceconverter.room.entities.FiatCoinExchange

@Database(entities = [CoinGeckoExchangeRate::class, FiatCoinExchange::class], version = 1, exportSchema = false)
@TypeConverters(RoomConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun priceConverterDao(): PriceConverterDao
}
