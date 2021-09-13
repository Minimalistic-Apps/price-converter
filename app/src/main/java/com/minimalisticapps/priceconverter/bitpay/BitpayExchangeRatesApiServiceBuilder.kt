package com.minimalisticapps.priceconverter.bitpay

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BitpayExchangeRatesApiServiceBuilder {

    private const val BASE_URL = "https://bitpay.com/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build() // Doesn't require the adapter
    }

    val API_SERVICE_BITPAY: BitpayExchangeRatesApiService =
        getRetrofit().create(BitpayExchangeRatesApiService::class.java)
}