package com.example.minimalisticpriceconverter.abstractapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ExchangeRatesAbstractApiService {
    @GET("v1/live")
    Call<ExchangeRatesResponse> get(@Query("api_key") String apiKey, @Query("base") String base);
}
