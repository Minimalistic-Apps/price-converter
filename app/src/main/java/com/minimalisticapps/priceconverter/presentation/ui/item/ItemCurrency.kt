package com.minimalisticapps.priceconverter.presentation.ui.item

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
import androidx.compose.ui.unit.sp
import com.minimalisticapps.priceconverter.common.utils.getFlagsForCurrency
import com.minimalisticapps.priceconverter.data.repository.priceconverter.CurrencyRate

@Composable
fun CoinItem(
    coin: CurrencyRate,
    onItemClick: (CurrencyRate) -> Unit,
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
            .padding(vertical = 14.dp, horizontal = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val flags = getFlagsForCurrency(coin.code)

        Text(
            text = "${coin.name} (${coin.code}) ${flags.joinToString("")}",
            style = MaterialTheme.typography.body1,
            fontSize = 14.sp,
            modifier = Modifier
                .weight(8f)
                .fillMaxWidth()
        )
    }
}
