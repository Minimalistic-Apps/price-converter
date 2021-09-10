package com.example.minimalisticpriceconverter.bitpay

data class BitpayExchangeRate(
    val code: String,
    val rate: Double
)


data class BitpayExchangeRatesResponse(
    val data: List<BitpayExchangeRate>
)
