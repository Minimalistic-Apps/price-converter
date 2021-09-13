package com.minimalisticapps.priceconverter.ratesapiplugin


interface RatesApiPlugin {

    fun call(token: String, callback: Callback)

}