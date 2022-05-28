package com.minimalisticapps.priceconverter.presentation.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minimalisticapps.priceconverter.R
import com.minimalisticapps.priceconverter.room.entities.BitPayCoinWithFiatCoin
import com.minimalisticapps.priceconverter.room.entities.FiatCoinExchange

@Composable
fun FiatCoinItem(
    bitPayCoinWithFiatCoin: BitPayCoinWithFiatCoin,
    onValueChanged: (BitPayCoinWithFiatCoin, Double) -> Unit,
    onLongPress: () -> Unit,
    onDeleteClick: (FiatCoinExchange) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            onLongPress()
                        }
                    )
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3.0f)
            ) {
                TextInputShitCoin(
                    onValueChange = { text ->
                        onValueChanged(bitPayCoinWithFiatCoin, text.toDouble())
                    },
                    rate = bitPayCoinWithFiatCoin.bitPayExchangeRate.rate,
                    fiatCoinExchange = bitPayCoinWithFiatCoin.fiatCoinExchange
                )
            }

            Text(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                text = bitPayCoinWithFiatCoin.fiatCoinExchange.code,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 10.dp)
            )

            Image(
                painterResource(R.drawable.ic_delete),
                "content description",
                modifier = Modifier
                    .padding(15.dp)
                    .clickable { onDeleteClick(bitPayCoinWithFiatCoin.fiatCoinExchange) }
            )
        }
        Text(
            text = "1 ${bitPayCoinWithFiatCoin.fiatCoinExchange.code} = ${bitPayCoinWithFiatCoin.bitPayExchangeRate.oneShitCoinValueString} BTC",
            style = MaterialTheme.typography.body1,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp, horizontal = 40.dp)
        )
    }
}
