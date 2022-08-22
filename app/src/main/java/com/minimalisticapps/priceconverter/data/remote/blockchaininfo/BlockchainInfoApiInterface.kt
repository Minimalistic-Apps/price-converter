package com.minimalisticapps.priceconverter.data.remote.blockchaininfo

import retrofit2.http.GET

interface BlockchainInfoApiInterface {
    @GET("ticker")
    suspend fun getExchangeRates(): Map<String, BlockchainInfoApiRate>
}
