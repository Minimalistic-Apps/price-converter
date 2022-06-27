package com.minimalisticapps.priceconverter.data.remote

import com.minimalisticapps.priceconverter.data.remote.dto.BitPayExchangeRatesResponse
import retrofit2.http.GET

interface ApiInterface {
    @GET("api/v3/exchange_rates")
    suspend fun get(): BitPayExchangeRatesResponse
}