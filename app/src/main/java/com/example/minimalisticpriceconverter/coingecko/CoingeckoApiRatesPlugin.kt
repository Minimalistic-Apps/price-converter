package com.example.minimalisticpriceconverter.coingecko

import com.example.minimalisticpriceconverter.ratesapiplugin.BITCOIN_PRECISION
import com.example.minimalisticpriceconverter.ratesapiplugin.Callback
import com.example.minimalisticpriceconverter.ratesapiplugin.RatesApiPlugin
import retrofit2.Call
import retrofit2.Response
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class CoingeckoApiRatesPlugin : RatesApiPlugin {
    private var api = ExchangeRatesAbstractApiServiceBuilder.apiService

    override fun call(token: String, callback: Callback) {
        val call: Call<ExchangeRatesResponse> = api.get()

        call.enqueue(object : retrofit2.Callback<ExchangeRatesResponse> {
            override fun onResponse(
                call: Call<ExchangeRatesResponse>,
                response: Response<ExchangeRatesResponse>
            ) {
                val body = response.body()

                if (body != null) {
                    callback.onSuccess(body.rates.entries.associate { it ->
                        it.key.uppercase(Locale.getDefault()) to BigDecimal.valueOf(1)
                            .divide(
                                BigDecimal.valueOf(it.value.value),
                                BITCOIN_PRECISION,
                                RoundingMode.HALF_UP
                            )
                    })
                } else {
                    callback.onFailure(Exception("Error: missing body in the response!"))
                }
            }

            override fun onFailure(call: Call<ExchangeRatesResponse>, throwable: Throwable) {
                callback.onFailure(throwable)
            }
        })
    }
}