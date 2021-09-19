package com.minimalisticapps.priceconverter

import android.util.Log
import java.math.BigDecimal

fun parseBigDecimalFromString(input: String): BigDecimal? {
    return try {
        BigDecimal(input)
    } catch (e: NumberFormatException) {
        Log.v("parseBigDecimalFromString", "input: $input $e")

        null
    }
}