package com.minimalisticapps.priceconverter

import java.math.BigDecimal

fun parseBigDecimalFromString(input: String): BigDecimal? {
    return try {
        BigDecimal(input)
    } catch (e: NumberFormatException) {
        null
    }
}