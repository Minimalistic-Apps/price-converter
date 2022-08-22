package com.minimalisticapps.priceconverter.data.remote.bitpay

import java.math.BigDecimal

data class BitpayApiRate(
    var code: String,
    var rate: BigDecimal?,
    val name: String,
)
