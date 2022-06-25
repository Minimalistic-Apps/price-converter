package com.minimalisticapps.priceconverter.presentation.ui.widget

import com.minimalisticapps.priceconverter.common.utils.formatBtc
import com.minimalisticapps.priceconverter.common.utils.to8Decimal
import java.math.BigDecimal

val EIGHT_DECIMAL_PLACES = BigDecimal("0.00000001")
val SIXTEEN_DECIMAL_PLACES = BigDecimal("0.000000000000001")


fun formatUnitOfShitcoinPrice(value: BigDecimal?): String {
    if (value == null) {
        return "n/a"
    }

    return when {
        value >= EIGHT_DECIMAL_PLACES -> formatBtc(value)
        value >= SIXTEEN_DECIMAL_PLACES -> value.toString()
            .to8Decimal()
        else -> {
            return "0"
        }
    }
}
