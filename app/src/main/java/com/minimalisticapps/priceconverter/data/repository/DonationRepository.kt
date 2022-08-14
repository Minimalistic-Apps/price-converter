package com.minimalisticapps.priceconverter.data.repository

import com.minimalisticapps.priceconverter.data.remote.donationserver.ClaimBody
import com.minimalisticapps.priceconverter.data.remote.donationserver.DonationServerApiInterface
import com.minimalisticapps.priceconverter.data.remote.donationserver.DonationServerGetClaimResponse
import com.minimalisticapps.priceconverter.data.remote.donationserver.DonationServerMakeClaimResponse
import javax.inject.Inject

class DonationRepository @Inject constructor(
    private val donationServerApi: DonationServerApiInterface,
) {
    suspend fun makeClaim(claim: String): DonationServerMakeClaimResponse =
        donationServerApi.makeClaim(ClaimBody(claim))

    suspend fun getClaim(claim: String): DonationServerGetClaimResponse =
        donationServerApi.getClaim(claim)
}
