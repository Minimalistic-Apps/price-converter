package com.minimalisticapps.priceconverter.presentation.ui.widget

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.minimalisticapps.priceconverter.presentation.home.viewmodels.HomeViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun TextInputBtc(
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    TextField(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)
            .onFocusSelectAll(homeViewModel.textFiledValueBtc),
        value = homeViewModel.textFiledValueBtc.value,
        onValueChange = homeViewModel::setTextFieldValueBtc,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
    )
}
