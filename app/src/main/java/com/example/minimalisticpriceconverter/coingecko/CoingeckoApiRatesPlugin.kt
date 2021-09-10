package com.example.minimalisticpriceconverter.coingecko

import com.example.minimalisticpriceconverter.ratesapiplugin.BITCOIN_RATE_PRECISION_INTERNAL
import com.example.minimalisticpriceconverter.ratesapiplugin.Callback
import com.example.minimalisticpriceconverter.ratesapiplugin.RatesApiPlugin
import retrofit2.Call
import retrofit2.Response
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class CoingeckoApiRatesPlugin : RatesApiPlugin {
    private var api = CoingeckoExchangeRatesApiServiceBuilder.API_SERVICE_COINGECKO

    override fun call(token: String, callback: Callback) {
        val call: Call<CoingeckoExchangeRatesResponse> = api.get()

        call.enqueue(object : retrofit2.Callback<CoingeckoExchangeRatesResponse> {
            override fun onResponse(
                call: Call<CoingeckoExchangeRatesResponse>,
                responseCoingecko: Response<CoingeckoExchangeRatesResponse>
            ) {
                val body = responseCoingecko.body()

                if (body != null) {
                    callback.onSuccess(body.rates.entries.associate {
                        it.key.uppercase(Locale.getDefault()) to BigDecimal.valueOf(1)
                            .divide(
                                BigDecimal.valueOf(it.value.value),
                                BITCOIN_RATE_PRECISION_INTERNAL,
                                RoundingMode.HALF_UP
                            )
                    })
                } else {
                    callback.onFailure(Exception("Error: missing body in the response!"))
                }
            }

            override fun onFailure(
                call: Call<CoingeckoExchangeRatesResponse>,
                throwable: Throwable
            ) {
                callback.onFailure(throwable)
            }
        })
    }
}