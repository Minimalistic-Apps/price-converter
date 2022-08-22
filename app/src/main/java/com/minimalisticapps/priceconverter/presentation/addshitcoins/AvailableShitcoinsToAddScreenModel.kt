package com.minimalisticapps.priceconverter.presentation.addshitcoins

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.minimalisticapps.priceconverter.data.repository.priceconverter.PriceConverterRepository
import com.minimalisticapps.priceconverter.data.repository.priceconverter.SaveScreenCurrencyRecordUseCase
import com.minimalisticapps.priceconverter.room.entities.Shitcoin
import com.minimalisticapps.priceconverter.room.entities.ShitcoinOnScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AvailableShitcoinsToAddScreenModel @Inject constructor(
    application: Application,
    private val saveScreenCurrencyRecordUseCase: SaveScreenCurrencyRecordUseCase,
    private val priceConverterRepository: PriceConverterRepository
) : AndroidViewModel(application) {

    val shitcoins: LiveData<List<Shitcoin>> =
        priceConverterRepository.getShitcoinsFromDatabase().asLiveData()


    fun addShitcoinOnScreen(shitcoinOnScreen: ShitcoinOnScreen) {
        viewModelScope.launch {
            saveScreenCurrencyRecordUseCase.invoke(shitcoinOnScreen)
        }
    }
}
