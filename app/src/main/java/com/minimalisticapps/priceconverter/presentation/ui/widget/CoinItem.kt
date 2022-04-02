package com.minimalisticapps.priceconverter.presentation.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.minimalisticapps.priceconverter.data.remote.dto.BitPayExchangeRate

@Composable
fun CoinItem(
    coin: BitPayExchangeRate,
    onItemClick: (BitPayExchangeRate) -> Unit,
) {
    val interaction = remember {
        MutableInteractionSource()
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = { onItemClick(coin) },
                interactionSource = interaction,
                indication = rememberRipple(bounded = true)
            )
            .padding(vertical = 20.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${coin.name} (${coin.code})",
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .weight(8f)
                .fillMaxWidth()
        )
    }
}
