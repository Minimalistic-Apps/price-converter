package com.codesses.priceconverter.common

import android.app.Application
import android.content.Context
import com.codesses.priceconverter.common.utils.SharedPrefConfig
import com.codesses.priceconverter.interfaces.Bus
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppController: Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPrefConfig.initSharedConfig(configBus)
    }

    private val configBus = object : Bus {
        override fun getAppContext(): Context {
            return this@AppController
        }
    }
}