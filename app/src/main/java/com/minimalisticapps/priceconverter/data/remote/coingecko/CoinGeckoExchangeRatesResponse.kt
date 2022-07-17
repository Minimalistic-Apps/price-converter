package com.minimalisticapps.priceconverter.data.remote.coingecko

data class CoinGeckoExchangeRatesResponse(
    val rates: Map<String, CoinGeckoExchangeRate>
)
