package com.minimalisticapps.priceconverter.blockchaininfo

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BlockchainInfoExchangeRatesApiServiceBuilder {

    private const val BASE_URL = "https://blockchain.info/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build() // Doesn't require the adapter
    }

    val API_SERVICE_BLOCKCHAIN_INFO: BlockchainInfoExchangeRatesApiService =
        getRetrofit().create(BlockchainInfoExchangeRatesApiService::class.java)
}