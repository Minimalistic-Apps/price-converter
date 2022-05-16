package com.minimalisticapps.priceconverter.presentation.ui.widget

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.minimalisticapps.priceconverter.common.utils.parseBigDecimalFromString
import com.minimalisticapps.priceconverter.common.utils.toSatsFormat
import com.minimalisticapps.priceconverter.presentation.home.viewmodels.HomeViewModel
import com.minimalisticapps.priceconverter.room.entities.FiatCoinExchange

@Composable
fun TextInputShitCoin(
    rate: Double?,
    onValueChange: (String) -> Unit,
    fiatCoinExchange: FiatCoinExchange,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val searchText = remember {
        mutableStateOf(TextFieldValue())
    }
    val isFocused = remember {
        mutableStateOf(false)
    }

    if (viewModel.textFieldValueBtc.value.text.isNotEmpty() && !isFocused.value) {
        var string = viewModel.textFieldValueBtc.value.text.replace(",", "")
        val double = parseBigDecimalFromString(string)
        if (double != null && rate != null) {
            string = double.toDouble().times(rate).toString()
            fiatCoinExchange.shitCoinValue = string.toSatsFormat()
            viewModel.updateFiatCoin(fiatCoinExchange)
            searchText.value = TextFieldValue(
                fiatCoinExchange.shitCoinValue,
                selection = TextRange(fiatCoinExchange.shitCoinValue.length)
            )
        }
    } else if (viewModel.textFieldValueBtc.value.text.isEmpty()) {
        fiatCoinExchange.shitCoinValue = ""
        viewModel.updateFiatCoin(fiatCoinExchange)
        searchText.value = TextFieldValue(
            fiatCoinExchange.shitCoinValue,
            selection = TextRange(fiatCoinExchange.shitCoinValue.length)
        )
    }


    OutlinedTextField(
        modifier =
        Modifier
            .onFocusChanged {
                isFocused.value = it.isFocused
                Log.e("TextInputShitCoin", "OnFocused")
                if (it.isFocused) {
                    searchText.value = searchText.value.copy(
                        selection = TextRange(0, searchText.value.text.length)
                    )
                }
            }
            .padding(10.dp)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(10.dp),
                brush = Brush.horizontalGradient(
                    listOf(Color.Black, Color.Black)
                )
            ),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Gray,
            backgroundColor = Color.Transparent,
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        value = searchText.value,
        onValueChange = { textFieldValue ->
            val text = textFieldValue.text
            Log.e("OnStart", "TextInputShitCoin: ${fiatCoinExchange.shitCoinValue}")
            if (rate != null) {
                if (text.isNotEmpty()) {
                    var numberString = text.replace(",", "")
                    if (numberString.startsWith(".")) {
                        numberString = "0$numberString"
                    }
                    val value = parseBigDecimalFromString(numberString)
                    if (value != null) {
                        if (numberString == value.toPlainString()) {
                            numberString = value.toString()
                            if (numberString.contains(".") && numberString.split(".").size == 2) {
                                if (value.toString().split(".")[1].length < 4) {
                                    fiatCoinExchange.shitCoinValue = numberString
                                    viewModel.updateFiatCoin(fiatCoinExchange)
                                    searchText.value = TextFieldValue(
                                        fiatCoinExchange.shitCoinValue,
                                        selection = TextRange(fiatCoinExchange.shitCoinValue.length)
                                    )
                                    onValueChange(value.toString())
                                }
                            } else if (!numberString.contains(".")) {
                                fiatCoinExchange.shitCoinValue = numberString
                                viewModel.updateFiatCoin(fiatCoinExchange)
                                searchText.value = TextFieldValue(
                                    fiatCoinExchange.shitCoinValue,
                                    selection = TextRange(fiatCoinExchange.shitCoinValue.length)
                                )
                                onValueChange(value.toString())
                            }
                        } else {
                            Log.d("TextInputshdhsdhsgd", "TextInput: $numberString")
                            fiatCoinExchange.shitCoinValue = numberString
                            viewModel.updateFiatCoin(fiatCoinExchange)
                            searchText.value = TextFieldValue(
                                fiatCoinExchange.shitCoinValue,
                                selection = TextRange(fiatCoinExchange.shitCoinValue.length)
                            )
                            onValueChange(value.toString())
                        }
                    }
                } else {
                    Log.d("TextInputladk", "TextInput: ${textFieldValue.text}")
                    fiatCoinExchange.shitCoinValue = textFieldValue.text
                    viewModel.updateFiatCoin(fiatCoinExchange)
                    searchText.value = TextFieldValue(
                        fiatCoinExchange.shitCoinValue,
                        selection = TextRange(fiatCoinExchange.shitCoinValue.length)
                    )
                }
            } else {
                Log.d("TextInputEmpty", "TextInput: ${textFieldValue.text}")
                fiatCoinExchange.shitCoinValue = textFieldValue.text
                viewModel.updateFiatCoin(fiatCoinExchange)
                searchText.value = TextFieldValue(
                    fiatCoinExchange.shitCoinValue,
                    selection = TextRange(fiatCoinExchange.shitCoinValue.length)
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
    )
}