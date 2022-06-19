package com.minimalisticapps.priceconverter.presentation.ui.widget

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.minimalisticapps.priceconverter.common.utils.formatBtc
import com.minimalisticapps.priceconverter.common.utils.parseBigDecimalFromString
import com.minimalisticapps.priceconverter.presentation.home.viewmodels.HomeViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun TextInputBtc(
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val count = remember {
        mutableStateOf(0)
    }

    TextField(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)
            .onFocusChanged {
                if (it.isFocused) {
                    homeViewModel.setTextFieldValueBtc(
                        homeViewModel.textFieldValueBtc.value.copy(
                            selection = TextRange(
                                0,
                                homeViewModel.textFieldValueBtc.value.text.length
                            )
                        )
                    )
                    count.value = 1
                } else {
                    count.value = 0
                }
            },
        value = homeViewModel.textFieldValueBtc.value,
        onValueChange = { textFieldValue ->
            if (homeViewModel.textFieldValueBtc.value.text != textFieldValue.text) {
                val text = textFieldValue.text
                if (text.isNotEmpty()) {
                    var numberString = text.replace(",", "")
                    if (numberString.startsWith(".")) {
                        numberString = "0$numberString"
                    }
                    val value = parseBigDecimalFromString(numberString)
                    if (value != null) {
                        if (numberString == value.toPlainString()) {
                            if (value.toString().contains(".") && value.toString()
                                    .split(".").size == 2
                            ) {
                                if (value.toString().split(".")[1].length < 9) {
                                    val formatted = formatBtc(
                                        value.toString().toBigDecimal()
                                    )
                                    Log.e("TextFieldValue", textFieldValue.toString())
                                    homeViewModel.setTextFieldValueBtc(
                                        textFieldValue.copy(
                                            formatted,
                                            TextRange(formatted.length)
                                        )
                                    )

                                }
                            } else if (!value.toString().contains(".")) {
                                homeViewModel.setTextFieldValueBtc(textFieldValue)
                            }
                        } else {
                            Log.e("TextInputBtc:NotPlain", numberString)
                            homeViewModel.setTextFieldValueBtc(
                                textFieldValue.copy(
                                    numberString,
                                    TextRange(numberString.length)
                                )
                            )
                        }
                    }
                } else {
                    homeViewModel.setTextFieldValueBtc(textFieldValue)
                }
            } else if (count.value == 1) {
                count.value = 0
            } else if (count.value == 0) {
                homeViewModel.setTextFieldValueBtc(textFieldValue)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
    )
}
