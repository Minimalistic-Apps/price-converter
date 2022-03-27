package com.codesses.priceconverter.common.utils

import android.content.Context
import com.codesses.priceconverter.interfaces.Bus


object SharedPrefConfig {
    private lateinit var mBus: Bus

    fun initSharedConfig(bus: Bus) {
        synchronized(this) {
            mBus = bus
        }
    }

    fun getAppContext(): Context {
        return mBus.getAppContext()
    }
}