package com.minimalisticapps.priceconverter.presentation.ui.widget

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.minimalisticapps.priceconverter.common.utils.parseBigDecimalFromString
import com.minimalisticapps.priceconverter.presentation.home.viewmodels.HomeViewModel

@Composable
fun TextInputBtc(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onValueChange: () -> Unit
) {
    var searchText = homeViewModel.textFieldValueBtc.value
    OutlinedTextField(
        modifier =
        Modifier
            .onFocusChanged {
                if (it.isFocused) {
                    searchText = searchText.copy(
                        selection = TextRange(0, searchText.text.length)
                    )
                }
            }
            .padding(10.dp)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(10.dp),
                brush = Brush.horizontalGradient(
                    listOf(Color.Black, Color.Black)
                )),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Gray,
            backgroundColor = Color.Transparent,
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        value = searchText,
        onValueChange = { textFieldValue ->
            val text = textFieldValue.text
            if (text.isNotEmpty()) {
                var numberString = text.replace(",", "")
                if (numberString.startsWith(".")) {
                    numberString = "0$numberString"
                }
                val value = parseBigDecimalFromString(numberString)
                if (value != null) {
                    if (numberString == value.toPlainString()) {
                        if (value.toString().contains(".") && value.toString().split(".").size == 2) {
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
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
    )
}