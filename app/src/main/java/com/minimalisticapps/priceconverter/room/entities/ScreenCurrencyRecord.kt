package com.minimalisticapps.priceconverter.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This represents ADDED currency on the MainView Screen
 */
@Entity(tableName = "screen_currency_record")
data class ScreenCurrencyRecord(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "code")
    val code: String,
)
