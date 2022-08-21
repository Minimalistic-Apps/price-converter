package com.minimalisticapps.priceconverter.presentation.currencylist.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.minimalisticapps.priceconverter.data.repository.priceconverter.SaveScreenCurrencyRecordUseCase
import com.minimalisticapps.priceconverter.room.entities.ScreenCurrencyRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    application: Application,
    private val saveScreenCurrencyRecordUseCase: SaveScreenCurrencyRecordUseCase
) : AndroidViewModel(application) {
    fun insertFiatCoin(screenCurrencyRecord: ScreenCurrencyRecord) {
        viewModelScope.launch {
            saveScreenCurrencyRecordUseCase.invoke(screenCurrencyRecord)
        }
    }
}
