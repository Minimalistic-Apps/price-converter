package com.minimalisticapps.priceconverter.presentation.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minimalisticapps.priceconverter.R
import com.minimalisticapps.priceconverter.presentation.ui.theme.WhiteColor

@Composable
fun SetToolbar(
    title: String,
    onReload: () -> Unit,
    btcOrSats: String,
    onBtcOrSatsChange: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                top = 10.dp,
                end = 16.dp,
                bottom = 10.dp
            )
    ) {
        Text(
            textAlign = TextAlign.Start,
            text = title,
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(3.0f)
        )
        Text(
            text = "₿",
            color = Color.White,
            fontWeight = if (btcOrSats == "BTC") FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
        Switch(
            checked = btcOrSats == "Sats",
            onCheckedChange = { onBtcOrSatsChange() },
            colors = SwitchDefaults.colors(
                checkedThumbColor = WhiteColor,
                uncheckedThumbColor = WhiteColor,
                checkedTrackColor = WhiteColor,
                uncheckedTrackColor = WhiteColor,
            )
        )
        Text(
            text = "丰",
            color = Color.White,
            fontWeight = if (btcOrSats == "Sats") FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
        Image(
            painterResource(R.drawable.ic_refresh),
            "content description",
            modifier = Modifier
                .padding(start = 15.dp)
                .align(Alignment.CenterVertically)
                .clickable { onReload() }
        )
    }
}
