package com.minimalisticapps.priceconverter.presentation.ui.widget

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.minimalisticapps.priceconverter.presentation.PriceConverterCornerShape
import com.minimalisticapps.priceconverter.presentation.home.viewmodels.HomeViewModel


@SuppressLint("UnrememberedMutableState")
@Composable
fun TextInputBtc(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onValueChange: () -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .onFocusSelectAll(homeViewModel._textFiledValueBtc)
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)
            .border(
                width = 1.dp,
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
        value = homeViewModel._textFiledValueBtc.value,
        onValueChange = {
            val hasTextChanged = it.text != homeViewModel._textFiledValueBtc.value.text
            homeViewModel._textFiledValueBtc.value = it
            if (hasTextChanged) {
                onValueChange()
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
    )
}
