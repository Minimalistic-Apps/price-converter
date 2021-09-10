package com.example.minimalisticpriceconverter.bitpay

import com.example.minimalisticpriceconverter.ratesapiplugin.BITCOIN_RATE_PRECISION_INTERNAL
import com.example.minimalisticpriceconverter.ratesapiplugin.Callback
import com.example.minimalisticpriceconverter.ratesapiplugin.RatesApiPlugin
import retrofit2.Call
import retrofit2.Response
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class BitpayApiRatesPlugin : RatesApiPlugin {
    private var api = BitpayExchangeRatesApiServiceBuilder.API_SERVICE_BITPAY

    override fun call(token: String, callback: Callback) {
        val call: Call<BitpayExchangeRatesResponse> = api.get()

        call.enqueue(object : retrofit2.Callback<BitpayExchangeRatesResponse> {
            override fun onResponse(
                call: Call<BitpayExchangeRatesResponse>,
                responseBitpay: Response<BitpayExchangeRatesResponse>
            ) {
                val body = responseBitpay.body()

                if (body != null) {
                    callback.onSuccess(body.data.map {
                        it.code.uppercase(Locale.getDefault()) to BigDecimal.valueOf(1)
                            .divide(
                                BigDecimal.valueOf(it.rate),
                                BITCOIN_RATE_PRECISION_INTERNAL,
                                RoundingMode.HALF_UP
                            )
                    }.toMap())
                } else {
                    callback.onFailure(Exception("Error: missing body in the response!"))
                }
            }

            override fun onFailure(call: Call<BitpayExchangeRatesResponse>, throwable: Throwable) {
                callback.onFailure(throwable)
            }
        })
    }
}