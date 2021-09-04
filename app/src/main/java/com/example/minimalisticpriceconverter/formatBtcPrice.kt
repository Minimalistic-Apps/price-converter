package com.example.minimalisticpriceconverter

import java.math.BigDecimal

fun formatBtcPrice(price: BigDecimal?): String {
    if (price == null) {
        return "n/a"
    }

    val s = price.toPlainString()
    var result = ""
    var dot = false
    var depth = 0

    for (ch in s) {
        if (ch == '.') {
            dot = true
        } else if (dot) {
            if (depth == 2 || (depth - 2) % 3 == 0) {
                result += ","
            }
            depth++
        }

        result += ch
    }

    return result
}