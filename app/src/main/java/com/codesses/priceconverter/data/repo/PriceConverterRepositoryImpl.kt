package com.codesses.priceconverter.data.repo

import com.codesses.priceconverter.data.remote.ApiInterface
import com.codesses.priceconverter.data.remote.dto.BitPayExchangeRatesResponse
import com.codesses.priceconverter.domain.repo.PriceConverterRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PriceConverterRepositoryImpl @Inject constructor(
    private val api: ApiInterface
) : PriceConverterRepository {
    //
//    override suspend fun getExchangeRates(): Flow<BitPayExchangeRatesResponse> = callbackFlow {
//        api.get().enqueue(object : Callback<BitPayExchangeRatesResponse> {
//            override fun onResponse(call: Call<BitPayExchangeRatesResponse>, response: Response<BitPayExchangeRatesResponse>) {
//                if (response.isSuccessful) {
//                        trySend(BitPayExchangeRatesResponse(response.body()?.data ?: ArrayList(), response.message()))
//                } else{
//                    trySend(BitPayExchangeRatesResponse(response.body()?.data ?: ArrayList(), response.message(), true))
//                }
//            }
//
//            override fun onFailure(call: Call<BitPayExchangeRatesResponse>, t: Throwable) {
//                trySend(BitPayExchangeRatesResponse(ArrayList(), t.localizedMessage ?: "Something went wrong", true))
//            }
//
//        })
//
//        awaitClose {
//
//        }
//    }
    override suspend fun getExchangeRates(): BitPayExchangeRatesResponse = api.get()
}