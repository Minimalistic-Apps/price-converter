package com.minimalisticapps.priceconverter.common.utils


object PCSharedStorage : SharedPrefHelper(PCSharedConfig.PREF_NAME) {

    private const val IS_DATA_LOADED = "is_data_loaded"
    private const val TIMES_AGO = "time_ago"
    private const val USD_ADD_ONCE_ON_APP_LAUNCH = "usd_add_once_on_app_launch"


    fun saveDataLoaded(value: Boolean) {
        saveBoolean(IS_DATA_LOADED, value)
    }

    fun isDataLoaded(): Boolean {
        return getBoolean(IS_DATA_LOADED, false)
    }


    fun saveTimesAgo(value: Long) {
        saveLong(TIMES_AGO, value)
    }

    fun getTimesAgo(): Long {
        return getLong(TIMES_AGO, 0L)
    }

    fun saveUsdAsDefault(value: Boolean) {
        saveBoolean(USD_ADD_ONCE_ON_APP_LAUNCH, value)
    }

    fun isUsdAddDefault(): Boolean {
        return getBoolean(USD_ADD_ONCE_ON_APP_LAUNCH, false)
    }
}