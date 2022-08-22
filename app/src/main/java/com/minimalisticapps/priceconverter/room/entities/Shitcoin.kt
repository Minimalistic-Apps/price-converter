package com.minimalisticapps.priceconverter.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "shitcoins")
data class Shitcoin(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "code")
    var code: String,

    @ColumnInfo(name = "name")
    val name: String,

    // How many Sats is worth one unit of fiat shitcoin
    @ColumnInfo(name = "sats_per_unit")
    var satsPerUnit: BigDecimal?,
)
