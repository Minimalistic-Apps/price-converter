package com.codesses.priceconverter.presentation.ui.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.codesses.priceconverter.common.Resource
import com.codesses.priceconverter.common.enums.AppEnums
import com.codesses.priceconverter.common.utils.SharedPrefHelper
import com.codesses.priceconverter.domain.usecase.GetCoinsUseCase
import com.codesses.priceconverter.presentation.states.CoinsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PriceConverterViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinsUseCase,
    application: Application,
    private val sharedPrefHelper: SharedPrefHelper
) : AndroidViewModel(application) {
    private val _state = mutableStateOf(CoinsState())
    val state: State<CoinsState> = _state
    val _isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)
    var isRefreshing : LiveData<Boolean> = _isRefreshing
    var isDataLoaded = false

    init {
        isDataLoaded = sharedPrefHelper.getBoolean(AppEnums.DATA_LOADED.value, false)
        getCoins()
    }

    @OptIn(InternalCoroutinesApi::class)
    @SuppressLint("RestrictedApi")
    fun getCoins() {
        viewModelScope.launch {
            getCoinUseCase(isDataLoaded).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        if (!isDataLoaded) {
                            sharedPrefHelper.saveBoolean(AppEnums.DATA_LOADED.value, true)
                        }
                        result.data?.collect {
                            _isRefreshing.value = false
                            _state.value = CoinsState(coins = it)
                        }
                    }
                    is Resource.Loading -> {
                        _state.value = CoinsState(true)
                    }
                    is Resource.Error -> {
                        _state.value = CoinsState(
                            error = result.message ?: "An unexpected error occurred"
                        )

                    }
                }
            }
        }
    }

    fun refreshData(){
        _isRefreshing.value = true
        isDataLoaded = false
        getCoins()
    }

}