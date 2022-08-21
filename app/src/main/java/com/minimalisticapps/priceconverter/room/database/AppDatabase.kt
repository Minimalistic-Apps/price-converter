package com.minimalisticapps.priceconverter.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.minimalisticapps.priceconverter.room.RoomConverter
import com.minimalisticapps.priceconverter.data.repository.priceconverter.Shitcoin
import com.minimalisticapps.priceconverter.room.dao.PriceConverterDao
import com.minimalisticapps.priceconverter.room.entities.ShitcoinOnScreen

@Database(
    entities = [Shitcoin::class, ShitcoinOnScreen::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(RoomConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun priceConverterDao(): PriceConverterDao

}
