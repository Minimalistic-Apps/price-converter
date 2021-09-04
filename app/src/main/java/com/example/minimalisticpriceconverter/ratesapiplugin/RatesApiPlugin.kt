package com.example.minimalisticpriceconverter.ratesapiplugin


interface RatesApiPlugin {

    fun call(token: String, callback: Callback)

}