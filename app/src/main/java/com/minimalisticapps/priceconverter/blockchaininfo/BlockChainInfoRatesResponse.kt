package com.minimalisticapps.priceconverter.blockchaininfo

data class BlockchainInfoExchangeRate(
    val last: Double
)

typealias BlockChainInfoRatesResponse = Map<String, BlockchainInfoExchangeRate>