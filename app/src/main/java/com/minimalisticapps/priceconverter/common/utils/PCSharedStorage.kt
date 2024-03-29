package com.minimalisticapps.priceconverter.common.utils

import java.util.*


object PCSharedStorage : SharedPrefHelper(PCSharedConfig.PREF_NAME) {
    private const val IS_DATA_LOADED = "is_data_loaded"
    private const val TIMES_AGO = "time_ago"
    private const val LAST_DONATION_REMINDER = "last_donation_reminder"
    private const val USD_ADD_ONCE_ON_APP_LAUNCH = "usd_add_once_on_app_launch"
    private const val BTC_OR_SATS = "btc_or_sats"
    private const val DONATION_CLAIM = "donation_claim"
    private const val DONATION_TOKEN = "donation_token"

    fun saveDataLoaded(value: Boolean) {
        saveBoolean(IS_DATA_LOADED, value)
    }

    fun isDataLoaded(): Boolean {
        return getBoolean(IS_DATA_LOADED, false)
    }

    fun getBtcOrSats(): String {
        return getString(BTC_OR_SATS, "BTC")
    }

    fun saveBtcOrSats(value: String) {
        saveString(BTC_OR_SATS, value)
    }

    fun getDonationClaim(): String {
        val token = getString(DONATION_CLAIM, "")
        if (token != "") {
            return token
        }

        val newToken = UUID.randomUUID().toString()
        saveDonationClaim(newToken)

        return newToken
    }

    private fun saveDonationClaim(token: String) {
        saveString(DONATION_CLAIM, token)
    }

    fun getDonationToken(): String? {
        val token = getString(DONATION_TOKEN, "")

        return if (token != "") {
            // Todo: validate token against public key!
            //       (We probably don't need to validate at all.
            //       This in open-source so anyone can just fork and change the code.)

            token
        } else {
            null
        }
    }

    fun saveDonationToken(token: String) {
        saveString(DONATION_TOKEN, token)
    }

    fun saveTimesAgo(value: Long) {
        saveLong(TIMES_AGO, value)
    }

    fun getTimesAgo(): Long {
        return getLong(TIMES_AGO, 0L)
    }

    fun saveLastDonationReminder(value: Long) {
        saveLong(LAST_DONATION_REMINDER, value)
    }

    fun getLastDonationReminder(): Long {
        return getLong(LAST_DONATION_REMINDER, 0L)
    }

    fun saveUsdAsDefault(value: Boolean) {
        saveBoolean(USD_ADD_ONCE_ON_APP_LAUNCH, value)
    }

    fun isUsdAddDefault(): Boolean {
        return getBoolean(USD_ADD_ONCE_ON_APP_LAUNCH, false)
    }
}
