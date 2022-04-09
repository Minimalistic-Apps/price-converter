package com.minimalisticapps.priceconverter.presentation.home.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.minimalisticapps.priceconverter.common.Resource
import com.minimalisticapps.priceconverter.common.utils.PCSharedStorage
import com.minimalisticapps.priceconverter.common.utils.formatBtc
import com.minimalisticapps.priceconverter.common.utils.isDiffLongerThat1hours
import com.minimalisticapps.priceconverter.common.utils.timeToTimeAgo
import com.minimalisticapps.priceconverter.domain.usecase.DeleteUseCase
import com.minimalisticapps.priceconverter.domain.usecase.GetCoinsUseCase
import com.minimalisticapps.priceconverter.domain.usecase.GetFiatCoinsUseCase
import com.minimalisticapps.priceconverter.presentation.states.CoinsState
import com.minimalisticapps.priceconverter.room.entities.BitPayCoinWithFiatCoin
import com.minimalisticapps.priceconverter.room.entities.FiatCoinExchange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinsUseCase,
    private val getFiatCoinUseCase: GetFiatCoinsUseCase,
    private val deleteUseCase: DeleteUseCase,
    application: Application
) : AndroidViewModel(application) {

    //    Variables
    private var isDataLoaded = false
    private var timeAgo: String = ""
    private var timeAgoLong: Long = 0L
    var ratesUpdatedAt: Date? = null
    var timerHandler: Handler = Handler(Looper.getMainLooper())

    //    Mutable states
    private val _isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)
    private val _state = mutableStateOf(CoinsState())
    private val _timeAgoState = mutableStateOf("")
    private val _isLongerThan1hour = mutableStateOf(false)
    private val _fiatCoinsList: MutableState<List<Pair<String, BitPayCoinWithFiatCoin>>> =
        mutableStateOf(emptyList())
    private var _textFiledValueBtc: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue())


    //    States
    val state: State<CoinsState> = _state
    val fiatCoinsListState: State<List<Pair<String, BitPayCoinWithFiatCoin>>> = _fiatCoinsList
    var isRefreshing: LiveData<Boolean> = _isRefreshing
    val timeAgoState: State<String> = _timeAgoState
    val isLongerThan1hour: State<Boolean> = _isLongerThan1hour
    val textFieldValueBtc: State<TextFieldValue> = _textFiledValueBtc

    private val updateTextTask = object : Runnable {
        override fun run() {
            if (ratesUpdatedAt != null) {
                updateUpdatedAgoText(timeAgoLong)
            }
            timerHandler.postDelayed(this, 1000)
        }
    }

    //    Init function
    init {
        isDataLoaded = PCSharedStorage.isDataLoaded()
        timeAgoLong = PCSharedStorage.getTimesAgo()
        if (timeAgoLong != 0L) {
            _timeAgoState.value = Calendar.getInstance().time.time.timeToTimeAgo(timeAgoLong)
            ratesUpdatedAt = Date(timeAgoLong)
            timerHandler.post(updateTextTask)
        }
        getCoins()
        getFiatCoins()
    }

    //    time ago handle function2
    private fun updateUpdatedAgoText(past: Long) {
        val timeAgo = Calendar.getInstance().time.time
        _isLongerThan1hour.value = timeAgo.isDiffLongerThat1hours(past)
        this@HomeViewModel.timeAgo = timeAgo.timeToTimeAgo(past)
        _timeAgoState.value = this@HomeViewModel.timeAgo
    }

    //    function for getting or inserting coins
    @OptIn(InternalCoroutinesApi::class)
    @SuppressLint("RestrictedApi")
    fun getCoins() {
        viewModelScope.launch {
            getCoinUseCase(isDataLoaded).collect { result ->
                when (result) {
                    is Resource.Success -> {

                        if (!isDataLoaded) {
                            PCSharedStorage.saveDataLoaded(true)
                            PCSharedStorage.saveTimesAgo(Calendar.getInstance().time.time)
                            timeAgoLong = Calendar.getInstance().time.time
                            ratesUpdatedAt = Date(Calendar.getInstance().time.time)
                            timerHandler.removeCallbacks(updateTextTask)
                            timerHandler.post(updateTextTask)
                            updateUpdatedAgoText(Calendar.getInstance().time.time)

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
                        _isRefreshing.value = false
                        _state.value = CoinsState(
                            error = result.message ?: "An unexpected error occurred"
                        )

                    }
                }
            }
        }
    }

    //    function for getting fiat coins
    fun getFiatCoins() {
        viewModelScope.launch {
            getFiatCoinUseCase().collect {
                _fiatCoinsList.value = it.map { bitPayCoinWithFiatCoin ->
                    Pair(_textFiledValueBtc.value.text, bitPayCoinWithFiatCoin)
                }
            }

        }
    }

    fun deleteFiatCoin(fiatCoinExchange: FiatCoinExchange) {
        viewModelScope.launch {
            deleteUseCase.invoke(fiatCoinExchange)
        }
    }

    fun setTextFieldValueBtc(text: String, bool: Boolean = false) {
        if (bool) {
            val formatted = formatBtc(text.toBigDecimal())
            _textFiledValueBtc.value = TextFieldValue(
                formatted,
                TextRange(formatted.length),
                TextRange(0, formatted.length)
            )
        } else {
            _textFiledValueBtc.value =
                TextFieldValue(text, TextRange(text.length), TextRange(0, text.length))
        }
//        getFiatCoins()
    }

    fun refreshData() {
        _isRefreshing.value = true
        isDataLoaded = false
        getCoins()
    }
}