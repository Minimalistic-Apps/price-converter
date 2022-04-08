package com.minimalisticapps.priceconverter.presentation.ui.widget

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.minimalisticapps.priceconverter.room.entities.BitPayCoinWithFiatCoin
import com.minimalisticapps.priceconverter.room.entities.FiatCoinExchange

@Composable
fun FiatCoinItem(
    pair: Pair<String, BitPayCoinWithFiatCoin>,
    onValueChanged: (BitPayCoinWithFiatCoin, Double) -> Unit,
    onLongPress: (FiatCoinExchange) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            onLongPress(pair.second.fiatCoinExchange)
                        }
                    )
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.width(250.dp)) {
                TextInputShitCoin(onValueChange = { text ->
                    onValueChanged(pair.second, text.toDouble())
                },
                    rate = pair.second.bitPayExchangeRate.rate,
                    btcValue = pair.first
                )
            }

            Text(
                text = pair.second.fiatCoinExchange.code,
                textAlign = TextAlign.Start,
            )
        }
        Text(
            text = "1 ${pair.second.fiatCoinExchange.code} = ${pair.second.bitPayExchangeRate.oneShitCoinValueString} BTC",
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        )
    }
}