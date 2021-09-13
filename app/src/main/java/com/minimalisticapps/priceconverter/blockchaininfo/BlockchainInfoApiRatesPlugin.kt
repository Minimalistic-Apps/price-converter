package com.minimalisticapps.priceconverter.blockchaininfo

import com.minimalisticapps.priceconverter.ratesapiplugin.BITCOIN_RATE_PRECISION_INTERNAL
import com.minimalisticapps.priceconverter.ratesapiplugin.Callback
import com.minimalisticapps.priceconverter.ratesapiplugin.RatesApiPlugin
import retrofit2.Call
import retrofit2.Response
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class BlockchainInfoApiRatesPlugin : RatesApiPlugin {
    private var api = BlockchainInfoExchangeRatesApiServiceBuilder.API_SERVICE_BLOCKCHAIN_INFO

    override fun call(token: String, callback: Callback) {
        val call: Call<BlockChainInfoRatesResponse> = api.get()

        call.enqueue(object : retrofit2.Callback<BlockChainInfoRatesResponse> {
            override fun onResponse(
                call: Call<BlockChainInfoRatesResponse>,
                responseBlockchainInfo: Response<BlockChainInfoRatesResponse>
            ) {
                val body = responseBlockchainInfo.body()

                if (body != null) {
                    callback.onSuccess(body.entries.associate { it ->
                        it.key.uppercase(Locale.getDefault()) to BigDecimal.valueOf(1)
                            .divide(
                                BigDecimal.valueOf(it.value.last),
                                BITCOIN_RATE_PRECISION_INTERNAL,
                                RoundingMode.HALF_UP
                            )
                    })
                } else {
                    callback.onFailure(Exception("Error: missing body in the response!"))
                }
            }

            override fun onFailure(call: Call<BlockChainInfoRatesResponse>, throwable: Throwable) {
                callback.onFailure(throwable)
            }
        })
    }
}