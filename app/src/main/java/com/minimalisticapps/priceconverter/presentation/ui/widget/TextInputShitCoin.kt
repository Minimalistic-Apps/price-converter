package com.minimalisticapps.priceconverter.presentation.ui.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.minimalisticapps.priceconverter.common.utils.parseBigDecimalFromString
import com.minimalisticapps.priceconverter.room.entities.FiatCoinExchange
import java.math.BigDecimal

@Composable
fun TextInputShitCoin(
    onValueChange: (BigDecimal?) -> Unit,
    fiatCoinExchange: FiatCoinExchange,
) {
    val state = remember {
        mutableStateOf(
            TextFieldValue(
                fiatCoinExchange.shitCoinValue,
                TextRange(fiatCoinExchange.shitCoinValue.length)
            )
        )
    }

    TextField(
        modifier =
        Modifier
            .fillMaxWidth()
            .onFocusSelectAll(state)
            .padding(start = 15.dp, end = 0.dp, top = 10.dp, bottom = 10.dp),
        value = state.value,
        onValueChange = {
            val value = parseBigDecimalFromString(it.text)

            state.value = it
            onValueChange(value)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
    )
}
