package com.minimalisticapps.priceconverter.presentation.coinlist.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.minimalisticapps.priceconverter.domain.usecase.SaveFiatCoinUseCase
import com.minimalisticapps.priceconverter.room.entities.FiatCoinExchange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    application: Application,
    private val saveFiatCoinUseCase: SaveFiatCoinUseCase

) : AndroidViewModel(application) {

    fun insertFiatCoin(fiatCoinExchange: FiatCoinExchange)  {
        viewModelScope.launch {
            saveFiatCoinUseCase.invoke(fiatCoinExchange)
        }
    }
}