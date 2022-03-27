package com.codesses.priceconverter.common.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

open class SharedPrefHelper(preferenceName: String) {

    private var mSharedPreferences: SharedPreferences? = null

    init {
        mSharedPreferences = SharedPrefConfig.getAppContext()
            .getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
    }

    @Synchronized
    fun saveInt(key: String, value: Int) {
        mSharedPreferences?.edit()?.putInt(key, value)?.apply()
    }

    fun getInt(key: String, default: Int): Int {
        return mSharedPreferences?.getInt(key, default)!!
    }

    @Synchronized
    fun saveString(key: String, value: String) {
        mSharedPreferences?.edit()?.putString(key, value)?.apply()
    }

    fun getString(key: String, default: String): String {
        return mSharedPreferences?.getString(key, default)!!
    }

    @Synchronized
    fun saveBoolean(key: String, value: Boolean) {
        mSharedPreferences?.edit()?.putBoolean(key, value)?.apply()
    }

    fun getBoolean(key: String, default: Boolean): Boolean {
        return mSharedPreferences?.getBoolean(key, default)!!
    }


    @Synchronized
    fun saveFloat(key: String, value: Float) {
        mSharedPreferences?.edit()?.putFloat(key, value)?.apply()
    }

    fun getFloat(key: String, default: Float): Float {
        return mSharedPreferences?.getFloat(key, default)!!
    }

    fun removeKey(key: String) {
        mSharedPreferences?.edit()?.remove(key)?.commit()!!
    }

    fun saveObject(key: String, value: Any?) {
        var json = Gson().toJson(value)
        mSharedPreferences?.edit()?.putString(key, json)?.apply()
    }

    @Synchronized
    fun saveLong(key: String, value: Long) {
        mSharedPreferences?.edit()?.putLong(key, value)?.apply()
    }

    fun getLong(key: String, default: Long): Long {
        return mSharedPreferences?.getLong(key, default)!!
    }


}