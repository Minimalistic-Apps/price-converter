package com.minimalisticapps.priceconverter.presentation.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minimalisticapps.priceconverter.R
import com.minimalisticapps.priceconverter.presentation.ui.theme.PrimaryColorLight
import com.minimalisticapps.priceconverter.presentation.ui.theme.WhiteColor

@Composable
fun SetToolbar(
    title: String,
    onReload: () -> Unit,
    onDonateClick: () -> Unit,
    btcOrSats: String,
    onBtcOrSatsChange: () -> Unit,
    donationToken: String?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                top = 10.dp,
                end = 16.dp,
                bottom = 10.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(2.0f, fill = false)
//                .background(color = Color.Green)
        ) {
            Text(
                textAlign = TextAlign.Start,
                text = title,
                color = Color.White,
                fontSize = 20.sp,
                softWrap = false,
                overflow = TextOverflow.Ellipsis
//                modifier = Modifier.background(color = Color.Red)
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier =
            if (donationToken != null)
                Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1.0f, fill = false)
                    .widthIn(0.dp, 120.dp)
            else
                Modifier
                    .align(Alignment.CenterVertically)
                    .widthIn(120.dp, 120.dp)
        ) {
            if (donationToken != null) {
                Image(
                    painterResource(R.drawable.ic_hearth),
                    "Thank you!",
                    modifier = Modifier.clickable { onDonateClick() }
                )
            } else {
                Button(
                    onClick = { onDonateClick() },
                    content = {
                        Text(text = "⚡Donate⚡", softWrap = false)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor =
                        PrimaryColorLight
                    ),
                    contentPadding = PaddingValues(
                        start = 4.dp,
                        top = 0.dp,
                        end = 4.dp,
                        bottom = 0.dp
                    ),
//                    modifier = Modifier.width(100.dp)
                )
            }
        }
        Box(
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Row() {
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
                    ),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
                Text(
                    text = "丰",
                    color = Color.White,
                    fontWeight = if (btcOrSats == "Sats") FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 15.dp)
                )
                Image(
                    painterResource(R.drawable.ic_refresh),
                    "Refresh",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clickable { onReload() }
                )
            }
        }
    }
}

@Preview
@Composable
fun SetToolbarPreview() {
    SetToolbar(
        title = "Price Converter",
        btcOrSats = "BTC",
        onReload = {},
        onDonateClick = {},
        onBtcOrSatsChange = {},
        donationToken = null // "token"
    )
}
