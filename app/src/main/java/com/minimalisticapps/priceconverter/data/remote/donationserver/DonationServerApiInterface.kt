package com.minimalisticapps.priceconverter.data.remote.donationserver

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


data class ClaimBody(
    @SerializedName("claim") val claim: String,
)

interface DonationServerApiInterface {
    @POST("api/key/claim")
    suspend fun makeClaim(@Body dataModal: ClaimBody): DonationServerMakeClaimResponse

    @GET("api/key/claim/{claim}")
    suspend fun getClaim(@Path("claim") claim: String): DonationServerGetClaimResponse
}
