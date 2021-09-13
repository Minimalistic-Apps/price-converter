package com.minimalisticapps.priceconverter.blockchaininfo;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BlockchainInfoExchangeRatesApiService {
    @GET("ticker")
    Call<Map<String, BlockchainInfoExchangeRate>> get();
}
