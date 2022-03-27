package com.codesses.priceconverter.data.remote

import com.codesses.priceconverter.data.remote.dto.BitPayExchangeRatesResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("api/v3/exchange_rates")
    suspend fun get(): BitPayExchangeRatesResponse
}