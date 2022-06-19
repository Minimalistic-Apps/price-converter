package com.minimalisticapps.priceconverter.presentation.ui.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun TextInputShitCoin(
    state: MutableState<TextFieldValue>,
    onValueChange: (TextFieldValue) -> Unit,
) {
    TextField(
        modifier =
        Modifier
            .fillMaxWidth()
            .onFocusSelectAll(state)
            .padding(start = 15.dp, end = 0.dp, top = 10.dp, bottom = 10.dp),
        value = state.value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
    )
}
