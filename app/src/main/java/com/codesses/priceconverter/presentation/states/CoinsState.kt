package com.codesses.priceconverter.presentation.states

import com.codesses.priceconverter.data.remote.dto.BitPayExchangeRate

data class CoinsState(
    val isLoading: Boolean = false,
    val coins: List<BitPayExchangeRate>? = null,
    val error: String = "",
    val isRefreshing: Boolean = false
)