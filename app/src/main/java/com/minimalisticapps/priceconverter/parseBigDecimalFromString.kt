package com.minimalisticapps.priceconverter

import android.util.Log
import java.math.BigDecimal

fun parseBigDecimalFromString(input: String): BigDecimal? {
    // remove commas from Sats friendly formatting
    val strippedOfCommas = input.replace(",", "")
    return try {
        BigDecimal(strippedOfCommas)
    } catch (e: NumberFormatException) {
        Log.v("parseBigDecimalFromString", "input: $input $e")

        null
    }
}