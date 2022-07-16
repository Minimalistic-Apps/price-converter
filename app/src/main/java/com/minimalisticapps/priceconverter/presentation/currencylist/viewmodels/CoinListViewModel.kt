package com.minimalisticapps.priceconverter.presentation.currencylist.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    var _lnUrl: MutableLiveData<String> = MutableLiveData()
    var lnUrl: LiveData<String> = _lnUrl

    fun insertFiatCoin(fiatCoinExchange: FiatCoinExchange) {
        viewModelScope.launch {
            saveFiatCoinUseCase.invoke(fiatCoinExchange)
        }
    }
}
