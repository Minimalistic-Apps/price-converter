package com.example.minimalisticpriceconverter.blockchaininfo

data class BlockchainInfoExchangeRate(
    val last: Double
)

typealias BlockChainInfoRatesResponse = Map<String, BlockchainInfoExchangeRate>