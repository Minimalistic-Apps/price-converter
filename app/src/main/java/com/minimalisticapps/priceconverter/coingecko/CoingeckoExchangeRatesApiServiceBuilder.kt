package com.minimalisticapps.priceconverter.coingecko

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CoingeckoExchangeRatesApiServiceBuilder {

    private const val BASE_URL = "https://api.coingecko.com/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build() // Doesn't require the adapter
    }

    val API_SERVICE_COINGECKO: CoingeckoExchangeRatesApiService =
        getRetrofit().create(CoingeckoExchangeRatesApiService::class.java)
}