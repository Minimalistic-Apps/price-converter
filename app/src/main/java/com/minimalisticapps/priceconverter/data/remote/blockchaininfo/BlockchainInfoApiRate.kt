package com.minimalisticapps.priceconverter.data.remote.blockchaininfo

import java.math.BigDecimal

data class BlockchainInfoApiRate(
//    val 15m: BigDecimal,
    val last: BigDecimal,
//    val buy: BigDecimal,
//    val sell: BigDecimal,
    val symbol: String
)
