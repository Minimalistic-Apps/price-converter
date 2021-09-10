package com.example.minimalisticpriceconverter.coingecko;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ExchangeRatesAbstractApiService {
    @GET("api/v3/exchange_rates")
    Call<ExchangeRatesResponse> get();
}
