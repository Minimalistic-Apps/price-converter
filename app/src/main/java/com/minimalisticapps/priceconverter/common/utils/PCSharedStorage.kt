package com.minimalisticapps.priceconverter.common.utils


object PCSharedStorage : SharedPrefHelper(PCSharedConfig.PREF_NAME) {

    private const val IS_DATA_LOADED = "is_data_loaded"
    private const val TIMES_AGO = "time_ago"
    private const val IS_DARK_MODE_ENABLED = "is_dark_mode_enabled"


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

    fun saveDarkMode(value: Boolean) {
        saveBoolean(IS_DARK_MODE_ENABLED, value)
    }

    fun getDarkMode(): Boolean {
        return getBoolean(IS_DARK_MODE_ENABLED, false)
    }
}