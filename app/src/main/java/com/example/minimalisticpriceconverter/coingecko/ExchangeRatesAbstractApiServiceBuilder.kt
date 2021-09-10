package com.example.minimalisticpriceconverter.coingecko

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ExchangeRatesAbstractApiServiceBuilder {

    private const val BASE_URL = "https://api.coingecko.com/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build() // Doesn't require the adapter
    }

    val apiService: ExchangeRatesAbstractApiService =
        getRetrofit().create(ExchangeRatesAbstractApiService::class.java)
}