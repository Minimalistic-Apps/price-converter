package com.minimalisticapps.priceconverter.presentation.ui.widget

import com.minimalisticapps.priceconverter.common.utils.SATS_IN_BTC
import com.minimalisticapps.priceconverter.common.utils.formatBtc
import com.minimalisticapps.priceconverter.common.utils.formatSats
import com.minimalisticapps.priceconverter.common.utils.to8Decimal
import java.math.BigDecimal

val EIGHT_DECIMAL_PLACES = BigDecimal("0.00000001")
val SIXTEEN_DECIMAL_PLACES = BigDecimal("0.000000000000001")


fun formatUnitOfShitcoinPrice(
    value: BigDecimal?,
    btcOrSats: String,
): String {
    if (value == null) {
        return "n/a"
    }

    if (btcOrSats == "Sats") {
        return formatSats(value.multiply(SATS_IN_BTC))
            .padStart(16) + " Sats"
    }

    return when {
        value >= EIGHT_DECIMAL_PLACES -> formatBtc(value) + " BTC"
        value >= SIXTEEN_DECIMAL_PLACES -> value.toString().to8Decimal() + " BTC"
        else -> {
            return "0"
        }
    }
}
