package com.minimalisticapps.priceconverter.data.remote.donationserver

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST


data class ClaimBody(
    @SerializedName("claim") val claim: String,
)

interface DonationServerApiInterface {
    @POST("api/donation/key/claim")
    suspend fun makeClaim(@Body dataModal: ClaimBody): DonationServerMakeClaimResponse
}
