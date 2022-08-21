package com.minimalisticapps.priceconverter.data.repository.priceconverter

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

@Entity(tableName = "Coins")
data class ExchangeRate(
    @ColumnInfo(name = "code")
    var code: String,

    // Deprecated, figure out how to remove
    @ColumnInfo(name = "rate")
    @SerializedName("value")
    var _deprecated_rate: BigDecimal? = null,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "unit")
    @SerializedName("unit")
    val source: String = "",

    @ColumnInfo(name = "one_shit_coin_value")
    var oneUnitOfShitcoinInBTC: BigDecimal?,
)
