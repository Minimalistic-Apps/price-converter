package com.minimalisticapps.priceconverter.presentation.ui.widget

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.minimalisticapps.priceconverter.common.utils.parseBigDecimalFromString
import com.minimalisticapps.priceconverter.presentation.PriceConverterCornerShape
import com.minimalisticapps.priceconverter.presentation.home.viewmodels.HomeViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun TextInputBtc(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onValueChange: () -> Unit
) {
    val searchText = remember {
        mutableStateOf(TextFieldValue())
    }
    val isFocused = remember {
        mutableStateOf(false)
    }
    if (!isFocused.value) {
        Log.e("TextInputBtc", "first ")
        searchText.value = homeViewModel.textFieldValueBtc.value
    }


    OutlinedTextField(
        modifier =
        Modifier
            .fillMaxWidth()
            .onFocusChanged {
                isFocused.value = it.isFocused
                if (isFocused.value) {
                    Log.e("TextInputBtc", "focused")
                    searchText.value = TextFieldValue(
                        text = homeViewModel.textFieldValueBtc.value.text,
                        selection = TextRange(0, homeViewModel.textFieldValueBtc.value.text.length)
                    )
                }
            }
            .border(
                width = 0.5.dp,
                shape = PriceConverterCornerShape,
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
            Log.e("TextInputBtc", "onValueChange ")
            if (searchText.value.text != textFieldValue.text) {
                isFocused.value = false
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
                                    homeViewModel.setTextFieldValueBtc(value.toString(), true)
                                    onValueChange()
                                }
                            } else if (!value.toString().contains(".")) {
                                homeViewModel.setTextFieldValueBtc(value.toString(), true)
                                onValueChange()
                            }
                        } else {
                            homeViewModel.setTextFieldValueBtc(numberString, false)
                            onValueChange()
                        }
                    }
                } else {
                    homeViewModel.setTextFieldValueBtc(text, false)
                    onValueChange()
                }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
    )
}
