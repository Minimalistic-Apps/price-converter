package com.minimalisticapps.priceconverter.data.remote.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

@Entity(tableName = "Coins")
data class BitPayExchangeRate(
    @ColumnInfo(name = "code")
    var code: String,

    @ColumnInfo(name = "rate")
    @SerializedName("value")
    var rate: BigDecimal?,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "unit")
    @SerializedName("unit")
    val unit: String,

    @ColumnInfo(name = "one_shit_coin_value")
    var oneUnitOfShitcoinInBTC: BigDecimal?,
)
