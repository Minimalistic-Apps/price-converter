package com.codesses.priceconverter.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codesses.priceconverter.data.remote.dto.BitPayExchangeRate
import com.codesses.priceconverter.room.dao.PriceConverterDao

@Database(entities = [BitPayExchangeRate::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun priceConverterDao(): PriceConverterDao
}
