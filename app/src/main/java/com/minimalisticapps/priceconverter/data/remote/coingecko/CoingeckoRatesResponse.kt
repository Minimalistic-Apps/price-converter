package com.minimalisticapps.priceconverter.data.remote.coingecko

data class CoingeckoRatesResponse(
    val rates: Map<String, CoingeckoApiRate>
)
