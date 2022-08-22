package com.minimalisticapps.priceconverter.data.remote.coingecko

import retrofit2.http.GET

interface CoingeckoApiInterface {
    @GET("api/v3/exchange_rates")
    suspend fun getExchangeRates(): CoingeckoRatesResponse
}
