package com.minimalisticapps.priceconverter.ratesapiplugin

const val BITCOIN_PRECISION = 8 // 1 BTC is 10^8 satoshi

// There are fiat shitcoins that has much lower value then 1 sat
const val BITCOIN_RATE_PRECISION_INTERNAL = BITCOIN_PRECISION * 2