/*
 *
 * Created by Saad Iftikhar on 23/03/22, 5:19 PM
 * Copyright (c) 2021. All rights reserved
 *
 */

package com.minimalisticapps.priceconverter.presentation.ui.widget

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.minimalisticapps.priceconverter.common.utils.parseBigDecimalFromString
import com.minimalisticapps.priceconverter.common.utils.toSatsFormat

@Composable
fun TextInputShitCoin(
    rate: Double?,
    onValueChange: (String) -> Unit,
    btcValue: String
) {
    val searchTextState = remember { mutableStateOf(TextFieldValue()) }
    val isFocused = remember { mutableStateOf(false) }

    if (btcValue.isNotEmpty() && !isFocused.value) {
        var string = btcValue.replace(",", "")
        val double = parseBigDecimalFromString(string)
        if (double != null && rate != null) {
            string = double.toDouble().times(rate).toString()
            searchTextState.value = TextFieldValue(string.toSatsFormat(), TextRange(string.length))
        }
    } else if (btcValue.isEmpty()) {
        searchTextState.value = TextFieldValue("")
    }

    OutlinedTextField(
        modifier =
        Modifier
            .onFocusChanged {
                isFocused.value = it.isFocused
                if (it.isFocused) {
                    searchTextState.value = searchTextState.value.copy(
                        selection = TextRange(0, searchTextState.value.text.length)
                    )
                }
            }
            .padding(horizontal = 16.dp)
            .border(
                width = 0.5.dp,
                shape = CircleShape,
                brush = Brush.horizontalGradient(
                    listOf(MaterialTheme.colors.onBackground, MaterialTheme.colors.onBackground)
                )
            ),
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onBackground,
            backgroundColor = Color.Transparent,
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        value = searchTextState.value,
        onValueChange = { text ->
            handleConditions(text, rate, searchTextState, onValueChange)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
    )
}

fun handleConditions(
    inputFieldValue: TextFieldValue,
    rate: Double?,
    searchText: MutableState<TextFieldValue>,
    onValueChange: (String) -> Unit
) {
    val inputText = inputFieldValue.text

    if (rate != null) {
        if (inputText.isNotEmpty()) {
            var updatedInput = inputText.replace(",", "")
            if (updatedInput.startsWith("."))
                updatedInput = "0$updatedInput"

            val parseValue = parseBigDecimalFromString(updatedInput)
            val parseString = parseValue.toString()

            if (parseValue != null) {
                if (updatedInput == parseValue.toPlainString()) {
                    updatedInput = parseValue.toString()

                    if (updatedInput.contains(".") && updatedInput.split(".").size == 2) {
                        if (parseString.split(".")[1].length < 4) {
                            searchText.value = getSearchText(updatedInput)
                            onValueChange(parseString)
                        }
                    } else if (!updatedInput.contains(".")) {
                        searchText.value = getSearchText(updatedInput)
                        onValueChange(parseString)
                    }

                } else {
                    searchText.value = getSearchText(updatedInput)
                    onValueChange(parseString)
                }
            } else searchText.value = getSearchText(updatedInput)

        } else searchText.value = inputFieldValue

    }
}

fun getSearchText(value: String): TextFieldValue {
    return TextFieldValue(value, TextRange(value.length))
}