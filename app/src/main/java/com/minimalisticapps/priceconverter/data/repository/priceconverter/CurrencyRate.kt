package com.minimalisticapps.priceconverter.data.repository.priceconverter

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

/**
 * This class represents the Currency with rate.
 * List of this in DB is list of all available currencies.
 */
@Entity(tableName = "currency_rate")
data class CurrencyRate(
    @ColumnInfo(name = "code")
    var code: String,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "name")
    val name: String,

    // How many Sats is worth one unit of fiat shitcoin
    @ColumnInfo(name = "sats_per_unit")
    var satsPerUnit: BigDecimal?,
)
