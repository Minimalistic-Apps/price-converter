package com.minimalisticapps.priceconverter.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fiat_coin_exchange")
data class FiatCoinExchange(
    @ColumnInfo(name = "unit")
    val unit: String,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "code")
    val code: String,
    @ColumnInfo(name = "shit_coin_value")
    var shitCoinValue: String = ""
)