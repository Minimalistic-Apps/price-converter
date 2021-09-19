package com.minimalisticapps.priceconverter

import com.minimalisticapps.priceconverter.ratesapiplugin.BITCOIN_PRECISION
import java.math.BigDecimal

val ONE_SAT_IN_BTC = BigDecimal(0.00000001);

fun formatBtc(value: BigDecimal?): String {
    if (value == null) {
        return ""
    }

    val s = value.toPlainString()
    var result = ""
    var dot = false
    var depth = 0

    for (ch in s) {
        if (ch == '.') {
            dot = true
        } else if (dot) {
            if (depth >= BITCOIN_PRECISION) {
                break
            }

            if (depth == 2 || (depth - 2) % 3 == 0) {
                result += ","
            }
            depth++

        }

        result += ch
    }

    return result
}

fun formatBtcRate(price: BigDecimal?): String {
    if (price == null) {
        return "n/a"
    }

    if (price < ONE_SAT_IN_BTC) {
        return price.toString()
    }

    return formatBtc(price)
}