package com.minimalisticapps.priceconverter.presentation.home.viewmodels

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
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
import com.minimalisticapps.priceconverter.common.utils.*
import com.minimalisticapps.priceconverter.domain.usecase.DeleteUseCase
import com.minimalisticapps.priceconverter.domain.usecase.GetCoinsUseCase
import com.minimalisticapps.priceconverter.domain.usecase.GetFiatCoinsUseCase
import com.minimalisticapps.priceconverter.domain.usecase.PRECISION
import com.minimalisticapps.priceconverter.presentation.states.CoinsState
import com.minimalisticapps.priceconverter.room.entities.BitPayCoinWithFiatCoin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

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
    private val _shitcoinListState: MutableState<List<Pair<Int, BitPayCoinWithFiatCoin>>> =
        mutableStateOf(emptyList())
    var textFiledValueBtc: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue())

    var shitcoinInputsState: Map<Int, MutableState<TextFieldValue>> = emptyMap()

    //    States
    val state: State<CoinsState> = _state
    val shitcoinListState: State<List<Pair<Int, BitPayCoinWithFiatCoin>>> = _shitcoinListState
    var isRefreshing: LiveData<Boolean> = _isRefreshing
    val timeAgoState: State<String> = _timeAgoState
    val isLongerThan1hour: State<Boolean> = _isLongerThan1hour

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

    //    time ago handle function
    private fun updateUpdatedAgoText(past: Long) {
        val timeAgo = Calendar.getInstance().time.time
        _isLongerThan1hour.value = timeAgo.isDiffLongerThat1hours(past)
        this@HomeViewModel.timeAgo = timeAgo.timeToTimeAgo(past)
        _timeAgoState.value = this@HomeViewModel.timeAgo
    }

    //    function for getting or inserting coins
    @OptIn(InternalCoroutinesApi::class)
    fun getCoins() {
        viewModelScope.launch {
            getCoinUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {

                        PCSharedStorage.saveDataLoaded(true)
                        PCSharedStorage.saveTimesAgo(Calendar.getInstance().time.time)
                        timeAgoLong = Calendar.getInstance().time.time
                        ratesUpdatedAt = Date(Calendar.getInstance().time.time)
                        timerHandler.removeCallbacks(updateTextTask)
                        timerHandler.post(updateTextTask)
                        updateUpdatedAgoText(Calendar.getInstance().time.time)

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
            getFiatCoinUseCase().collect { it ->
                var position = 0
                _shitcoinListState.value = it.map { bitPayCoinWithFiatCoin ->
                    Pair(position++, bitPayCoinWithFiatCoin)
                }

                shitcoinInputsState = _shitcoinListState.value.associate { it2 ->
                    it2.first to mutableStateOf(TextFieldValue())
                }
            }

        }
    }

    fun deleteFiatCoin(index: Int?) {
        val shitcoin = _shitcoinListState.value.find { it.first == index } ?: return

        viewModelScope.launch {
            deleteUseCase.invoke(shitcoin.second.fiatCoinExchange)
        }
    }

    fun updateBitcoinAmount(it: TextFieldValue) {
        // when we deleted just a comma we want to move cursor
        val normalizedState = textFiledValueBtc.value.text.replace(",", "")
        val normalizedNewValue = it.text.replace(",", "")
        if (normalizedState == normalizedNewValue) {
            textFiledValueBtc.value = TextFieldValue(
                text = textFiledValueBtc.value.text,
                selection = TextRange(
                    max(0, it.selection.start),
                    max(0, min(textFiledValueBtc.value.text.length, it.selection.end))
                )
            )

            return
        }

        // When actual number is changed, we have to adjust the selection
        // in case some comma was added
        val commasBefore = it.text.count { it == ',' }
        val formatted = formatBtcString(it.text)
        val commasAfter = formatted.count { it == ',' }

        val diff = commasAfter - commasBefore

        textFiledValueBtc.value = TextFieldValue(
            text = formatted,
            selection = TextRange(
                max(0, it.selection.start + diff),
                max(0, min(formatted.length, it.selection.end + diff))
            )
        )

        recalculateByBitcoin()
    }

    private fun updateBitcoinAmountWithoutRecalculate(value: BigDecimal) {
        textFiledValueBtc.value = TextFieldValue(text = formatBtc(value))
    }

    private fun recalculateByShitcoin(shitcoinIndex: Int) {
        val shitcoin =
            shitcoinListState.value.find { it.first == shitcoinIndex }?.second ?: return
        val shitcoinInput = shitcoinInputsState[shitcoinIndex]?.value?.text ?: return

        val shitcoinAmount =
            parseBigDecimalFromString(shitcoinInput) ?: return
        val oneUnitOfShitcoinInBTC =
            shitcoin.bitPayExchangeRate.oneUnitOfShitcoinInBTC ?: return
        val bitcoinPrice = oneUnitOfShitcoinInBTC.multiply(shitcoinAmount)
        updateBitcoinAmountWithoutRecalculate(bitcoinPrice)


        shitcoinListState.value.forEach {
            if (shitcoinIndex != it.first) {
                val itOneUnitOfShitcoinInBtc =
                    it.second.bitPayExchangeRate.oneUnitOfShitcoinInBTC ?: return

                val newValue = shitcoinAmount.multiply(
                    oneUnitOfShitcoinInBTC.divide(
                        itOneUnitOfShitcoinInBtc,
                        PRECISION,
                        RoundingMode.HALF_UP
                    )
                )

                shitcoinInputsState[it.first]?.value =
                    TextFieldValue(text = newValue.toPlainString())
            }
        }
    }

    private fun recalculateByBitcoin() {
        val amountBitcoin = parseBigDecimalFromString(textFiledValueBtc.value.text) ?: return

        shitcoinListState.value.forEach {
            val itOneUnitOfShitcoinInBtc =
                it.second.bitPayExchangeRate.oneUnitOfShitcoinInBTC

            if (itOneUnitOfShitcoinInBtc != null) {
                val newAmount = amountBitcoin.divide(
                    itOneUnitOfShitcoinInBtc,
                    PRECISION,
                    RoundingMode.HALF_UP
                )
                Log.i(
                    "recalculateByBitcoin",
                    it.first.toString() + " " + it.second.fiatCoinExchange.code + " " + newAmount.toPlainString()
                )

                shitcoinInputsState[it.first]?.value =
                    TextFieldValue(text = newAmount.toPlainString())
            }
        }

    }

    fun refreshData() {
        _isRefreshing.value = true
        isDataLoaded = false
        getCoins()
    }

    private fun updateShitcoinWithoutRecalculate(shitcoinIndex: Int, value: BigDecimal) {
        val state = shitcoinInputsState[shitcoinIndex] ?: return
        state.value = TextFieldValue(text = value.toPlainString())
    }

    fun updateShitcoin(shitcoinIndex: Int, input: TextFieldValue) {
        val state = shitcoinInputsState[shitcoinIndex] ?: return
        state.value = input

        val value = parseBigDecimalFromString(input.text) ?: return

        updateShitcoinWithoutRecalculate(shitcoinIndex, value)
        recalculateByShitcoin(shitcoinIndex)
    }
}
