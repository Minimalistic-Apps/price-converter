package com.minimalisticapps.priceconverter.presentation.ui.widget

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
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
import com.minimalisticapps.priceconverter.common.utils.parseBigDecimalFromString
import com.minimalisticapps.priceconverter.common.utils.toSatsFormat

@Composable
fun TextInputShitCoin(
    rate: Double?,
    onValueChange: (String) -> Unit,
    btcValue: String
) {
    val searchText = remember {
        mutableStateOf(TextFieldValue())
    }
    val isFocused = remember {
        mutableStateOf(false)
    }

    if (btcValue.isNotEmpty() && !isFocused.value) {
        var string = btcValue.replace(",", "")
        val double = parseBigDecimalFromString(string)
        if (double != null && rate != null) {
            string = double.toDouble().times(rate).toString()
            searchText.value = TextFieldValue(string.toSatsFormat(), TextRange(string.length))
        }
    } else if (btcValue.isEmpty()) {
        searchText.value = TextFieldValue("")
    }

    OutlinedTextField(
        modifier =
        Modifier
            .onFocusChanged {
                isFocused.value = it.isFocused
                if (it.isFocused) {
                    searchText.value = searchText.value.copy(
                        selection = TextRange(0, searchText.value.text.length)
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
        value = searchText.value,
        onValueChange = { textFieldValue ->
            val text = textFieldValue.text
            if (rate != null) {
                if (text.isNotEmpty()) {
                    var numberString = text.replace(",", "")
                    if (numberString.startsWith(".")) {
                        numberString = "0$numberString"
                        Log.d("TextInput", "TextInput: $numberString")
                    }
                    val value = parseBigDecimalFromString(numberString)
                    if (value != null) {
                        if (numberString == value.toPlainString()) {
                            numberString = value.toString()
                            if (numberString.contains(".") && numberString.split(".").size == 2) {
                                if (value.toString().split(".")[1].length < 4) {
                                    searchText.value =
                                        TextFieldValue(numberString, TextRange(numberString.length))
                                    onValueChange(value.toString())
                                }
                            } else if (!numberString.contains(".")) {
                                searchText.value =
                                    TextFieldValue(numberString, TextRange(numberString.length))
                                onValueChange(value.toString())
                            }
                        } else {
                            searchText.value =
                                TextFieldValue(numberString, TextRange(numberString.length))
                            onValueChange(value.toString())
                        }
                    } else {
                        searchText.value =
                            TextFieldValue(numberString, TextRange(numberString.length))
                    }
                } else {
                    searchText.value = textFieldValue
                }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
    )
}