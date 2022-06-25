package com.minimalisticapps.priceconverter.data

import androidx.room.TypeConverter
import java.math.BigDecimal

class RoomConverter {
    @TypeConverter
    fun fromString(value: String?): BigDecimal? {
        if (value.isNullOrBlank()) return BigDecimal.ZERO
        return value.toBigDecimalOrNull() ?: BigDecimal.ZERO
    }

    @TypeConverter
    fun amountToString(bigDecimal: BigDecimal?): String {
        return bigDecimal?.toPlainString() ?: ""
    }
}
