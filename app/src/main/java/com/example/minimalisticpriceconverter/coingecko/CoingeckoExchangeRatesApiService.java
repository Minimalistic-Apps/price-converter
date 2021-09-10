package com.example.minimalisticpriceconverter.coingecko;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CoingeckoExchangeRatesApiService {
    @GET("api/v3/exchange_rates")
    Call<CoingeckoExchangeRatesResponse> get();
}
