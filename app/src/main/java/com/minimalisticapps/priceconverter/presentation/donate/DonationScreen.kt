package com.minimalisticapps.priceconverter.presentation.donate

import android.app.Activity
import android.content.ActivityNotFoundException
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DonationScreen(donationViewModel: DonationViewModel = hiltViewModel()) {

    LaunchedEffect(true) {
        donationViewModel.userOpenedScreenWithDonate()
    }

    val mContext = LocalContext.current as Activity

    donationViewModel.error.observe(LocalLifecycleOwner.current) { it ->
        it.getContentIfNotHandled()?.let {
            Toast.makeText(mContext, it, Toast.LENGTH_LONG).show()
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp, start = 25.dp, end = 25.dp)
        ) {
            Text(
                text = "Donate!",
                textAlign = TextAlign.Center,
                fontSize = 35.sp
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp, start = 25.dp, end = 25.dp)
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = buildAnnotatedString {
                    append("Your contribution will helps us to deliver ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Free Open-Source Privacy-Respecting Minimalistic")
                    }
                    append(" apps to everybody!")
                }
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp, start = 25.dp, end = 25.dp)
        ) {
            Text(
                text = "And we stop periodically bugging you about it \uD83D\uDE09",
                textAlign = TextAlign.Center
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp, start = 25.dp, end = 25.dp)
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = "We accept donations in BTC over Lightning Network.",
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp, start = 25.dp, end = 25.dp)
        ) {
            val mUriHandler = LocalUriHandler.current

            Button(
                enabled = donationViewModel.lnUrl.value != null,
                onClick = {
                    val link = "lightning://${donationViewModel.lnUrl.value}"
                    try {
                        donationViewModel.userClickedPay()
                        mUriHandler.openUri(link)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(
                            mContext,
                            "No wallet supporting LNURL found.",
                            10f.toInt()
                        ).show()
                    }
                }) {
                Text(text = "Donate⚡", fontSize = 25.sp)
            }
        }

        if (donationViewModel.keyStatus.value.isNotEmpty()) {
            Log.i("x", donationViewModel.keyStatus.value.joinToString("\n"))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 25.dp, start = 25.dp, end = 25.dp)
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
                        )
                    )
                    .padding(all = 6.dp)
            ) {
                Text(
                    textAlign = TextAlign.Left,
                    text = donationViewModel.keyStatus.value.joinToString("\n"),
                    color = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
                )
            }
        }
    }
}
