package com.minimalisticapps.priceconverter.data.remote.bitpay

import retrofit2.http.GET

interface BitpayApiInterface {
    @GET("rates")
    suspend fun getExchangeRates(): BitpayRatesResponse
}
