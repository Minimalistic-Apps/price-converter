package com.minimalisticapps.priceconverter.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fiat_coin_exchange")
data class FiatCoinExchange(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "unit")
    val unit: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "code")
    val code: String
)