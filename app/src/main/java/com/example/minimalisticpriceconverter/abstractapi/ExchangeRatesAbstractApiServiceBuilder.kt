package com.example.minimalisticpriceconverter.abstractapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ExchangeRatesAbstractApiServiceBuilder {

    private const val BASE_URL = "https://exchange-rates.abstractapi.com/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build() //Doesn't require the adapter
    }

    val apiService: ExchangeRatesAbstractApiService =
        getRetrofit().create(ExchangeRatesAbstractApiService::class.java)
}