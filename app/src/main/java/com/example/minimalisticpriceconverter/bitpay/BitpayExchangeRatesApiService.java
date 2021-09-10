package com.example.minimalisticpriceconverter.bitpay;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BitpayExchangeRatesApiService {
    @GET("rates")
    Call<BitpayExchangeRatesResponse> get();
}
