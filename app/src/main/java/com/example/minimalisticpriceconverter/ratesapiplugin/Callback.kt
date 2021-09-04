package com.example.minimalisticpriceconverter.ratesapiplugin

import java.math.BigDecimal


interface Callback {
    fun onSuccess(data: Map<String, BigDecimal>)

    fun onFailure(t: Throwable)
}