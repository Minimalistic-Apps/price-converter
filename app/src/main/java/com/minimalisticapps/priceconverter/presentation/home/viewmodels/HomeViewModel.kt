package com.minimalisticapps.priceconverter.presentation.home.viewmodels

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.minimalisticapps.priceconverter.common.Resource
import com.minimalisticapps.priceconverter.common.utils.*
import com.minimalisticapps.priceconverter.data.repository.priceconverter.DeleteUseCase
import com.minimalisticapps.priceconverter.data.repository.priceconverter.GetCoinsUseCase
import com.minimalisticapps.priceconverter.data.repository.priceconverter.GetFiatCoinsUseCase
import com.minimalisticapps.priceconverter.data.repository.priceconverter.SaveFiatCoinUseCase
import com.minimalisticapps.priceconverter.presentation.states.CoinsState
import com.minimalisticapps.priceconverter.room.entities.ExchangeRateWithFiatCoin
import com.minimalisticapps.priceconverter.room.entities.FiatCoinExchange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import javax.inject.Inject

const val DONATION_REMINDER_DELAY_MS = 14 * 24 * 3600 * 1000 // 14 days

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinsUseCase,
    private val getFiatCoinUseCase: GetFiatCoinsUseCase,
    private val deleteUseCase: DeleteUseCase,
    private val saveFiatCoinUseCase: SaveFiatCoinUseCase,
    application: Application
) : AndroidViewModel(application) {

    //    Variables
    private var isDataLoaded = false
    private var timeAgo: String = ""
    private var timeAgoLong: Long = 0L
    var ratesUpdatedAt: Date? = null
    var timerHandler: Handler = Handler(Looper.getMainLooper())

    //    Mutable states
    private val _isRefreshing = mutableStateOf(false)
    private val _state = mutableStateOf(CoinsState())
    private val _timeAgoState = mutableStateOf("")
    private val _showDonationReminder = mutableStateOf(calculateShouldShowDonationReminder())
    private val _isLongerThan1hour = mutableStateOf(false)
    private val _shitcoinListState: MutableState<List<Pair<Int, ExchangeRateWithFiatCoin>>> =
        mutableStateOf(emptyList())
    var textFiledValueBtc: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue())

    var shitcoinInputsState: Map<String, MutableState<TextFieldValue>> = emptyMap()

    var selectedCoin: MutableState<String> = mutableStateOf("BTC")
    var btcOrSats: MutableState<String> = mutableStateOf("BTC") // "BTC" or "Sats"

    //    States
    val state: State<CoinsState> = _state
    val shitcoinListState: State<List<Pair<Int, ExchangeRateWithFiatCoin>>> = _shitcoinListState
    var isRefreshing: State<Boolean> = _isRefreshing
    val timeAgoState: State<String> = _timeAgoState
    val showDonationReminder: State<Boolean> = _showDonationReminder
    val isLongerThan1hour: State<Boolean> = _isLongerThan1hour

    private fun calculateShouldShowDonationReminder(): Boolean {
        // User has valid token
        if (PCSharedStorage.getDonationToken() != null) {
            return false
        }

        val currentTimestamp = Calendar.getInstance().time.time
        val lastReminder = PCSharedStorage.getLastDonationReminder()

        // For first run, we don't want to ask user immediately,
        // but only after the first time-period
        if (lastReminder == 0L) {
            PCSharedStorage.saveLastDonationReminder(currentTimestamp)
            return false
        }

        val threshold = currentTimestamp - DONATION_REMINDER_DELAY_MS.toLong()

        return lastReminder < threshold
    }

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
        btcOrSats.value = PCSharedStorage.getBtcOrSats()
        timeAgoLong = PCSharedStorage.getTimesAgo()
        if (timeAgoLong != 0L) {
            _timeAgoState.value = Calendar.getInstance().time.time.timeToTimeAgo(timeAgoLong)
            ratesUpdatedAt = Date(timeAgoLong)
            timerHandler.post(updateTextTask)
        }
        getCoins()
        getFiatCoins()
    }

    fun dismissReminder() {
        PCSharedStorage.saveLastDonationReminder(Calendar.getInstance().time.time)
        _showDonationReminder.value = calculateShouldShowDonationReminder()
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

    private fun getFiatCoins() {
        viewModelScope.launch {
            getFiatCoinUseCase().collect { it ->
                val listStateValue: MutableList<Pair<Int, ExchangeRateWithFiatCoin>> =
                    mutableListOf()
                val shitcoinInputsStateValue: MutableMap<String, MutableState<TextFieldValue>> =
                    mutableMapOf()

                it.forEachIndexed { index, shitcoin ->
                    listStateValue.add(Pair(index, shitcoin))

                    val code = shitcoin.fiatCoinExchange.code

                    shitcoinInputsStateValue[code] = shitcoinInputsState[code]
                        ?: mutableStateOf(TextFieldValue())
                }

                _shitcoinListState.value = listStateValue
                shitcoinInputsState = shitcoinInputsStateValue

                if (selectedCoin.value == "BTC") {
                    recalculateByBitcoin()
                } else {
                    val index =
                        _shitcoinListState.value.find { it2 -> it2.second.fiatCoinExchange.code == selectedCoin.value }?.first
                    if (index != null) {
                        recalculateByShitcoin(index)
                    }
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

    fun updateBitcoinAmount(value: TextFieldValue) {
        // if only selection changed don't do any magic, just change selection
        // do NOT recalculate, it would cumulate rounding error
        if (textFiledValueBtc.value.text == value.text) {
            textFiledValueBtc.value = value
            return
        }

        textFiledValueBtc.value =
            updateTextFieldModelWithCommas(
                textFiledValueBtc.value,
                value
            ) { if (btcOrSats.value == "BTC") formatBtcString(it) else formatSatsString(it) }

        recalculateByBitcoin()
    }

    private fun updateBitcoinAmountWithoutRecalculate(amount: BigDecimal) {
        if (btcOrSats.value == "BTC") {
            textFiledValueBtc.value = TextFieldValue(text = formatBtc(amount))
        } else {
            textFiledValueBtc.value =
                TextFieldValue(text = formatSats(amount.multiply(SATS_IN_BTC)))
        }
    }

    private fun recalculateByShitcoin(shitcoinIndex: Int) {
        val shitcoin =
            shitcoinListState.value.find { it.first == shitcoinIndex }?.second ?: return
        val shitcoinInput =
            shitcoinInputsState[shitcoin.fiatCoinExchange.code]?.value?.text ?: return

        val shitcoinAmount =
            parseBigDecimalFromString(shitcoinInput) ?: return
        val oneUnitOfShitcoinInBTC =
            shitcoin.exchangeRate.oneUnitOfShitcoinInBTC ?: return
        val bitcoinPrice = oneUnitOfShitcoinInBTC.multiply(shitcoinAmount)
        updateBitcoinAmountWithoutRecalculate(bitcoinPrice)


        shitcoinListState.value.forEach {
            if (shitcoinIndex != it.first) {
                val itOneUnitOfShitcoinInBtc =
                    it.second.exchangeRate.oneUnitOfShitcoinInBTC ?: return

                val newValue = shitcoinAmount.multiply(
                    oneUnitOfShitcoinInBTC.divide(
                        itOneUnitOfShitcoinInBtc,
                        AppConstants.STORAGE_PRECISION,
                        RoundingMode.HALF_UP
                    )
                )

                shitcoinInputsState[it.second.fiatCoinExchange.code]?.value =
                    TextFieldValue(text = formatFiatShitcoin(newValue))
            }
        }
    }

    private fun recalculateByBitcoin() {
        val amount = parseBigDecimalFromString(textFiledValueBtc.value.text) ?: return
        val amountBitcoin = if (btcOrSats.value == "BTC") amount else amount.divide(SATS_IN_BTC)

        shitcoinListState.value.forEach {
            val itOneUnitOfShitcoinInBtc =
                it.second.exchangeRate.oneUnitOfShitcoinInBTC

            if (itOneUnitOfShitcoinInBtc != null) {
                val newAmount = amountBitcoin.divide(
                    itOneUnitOfShitcoinInBtc,
                    AppConstants.STORAGE_PRECISION,
                    RoundingMode.HALF_UP
                )

                shitcoinInputsState[it.second.fiatCoinExchange.code]?.value =
                    TextFieldValue(text = formatFiatShitcoin(newAmount))
            }
        }

    }

    fun refreshData() {
        _isRefreshing.value = true
        isDataLoaded = false
        getCoins()
    }

    fun updateShitcoin(shitcoinIndex: Int, input: TextFieldValue) {
        val code = _shitcoinListState.value[shitcoinIndex].second.fiatCoinExchange.code
        val state = shitcoinInputsState[code] ?: return

        // if only selection changed don't do any magic, just change selection
        // do NOT recalculate, it would cumulate rounding error
        if (state.value.text == input.text) {
            state.value = input
            return
        }

        state.value =
            updateTextFieldModelWithCommas(state.value, input) {
                formatNumberString(
                    it,
                    SHITCOIN_PRECISION
                )
            }

        recalculateByShitcoin(shitcoinIndex)
    }

    fun switchBtcOrSats() {
        val original = btcOrSats.value
        btcOrSats.value = if (btcOrSats.value == "BTC") "Sats" else "BTC"
        PCSharedStorage.saveBtcOrSats(btcOrSats.value)

        if (original == "BTC" && btcOrSats.value == "Sats") {
            val bitcoinValue =
                parseBigDecimalFromString(textFiledValueBtc.value.text) ?: BigDecimal.ZERO

            updateBitcoinAmountWithoutRecalculate(bitcoinValue)
        }

        if (original == "Sats" && btcOrSats.value == "BTC") {
            val satsValue =
                parseBigDecimalFromString(textFiledValueBtc.value.text) ?: BigDecimal.ZERO

            updateBitcoinAmountWithoutRecalculate(satsValue.divide(SATS_IN_BTC))
        }

    }

    fun insertFiatCoin(fiatCoinExchange: FiatCoinExchange) {
        viewModelScope.launch {
            saveFiatCoinUseCase.invoke(fiatCoinExchange)
        }
    }
}
