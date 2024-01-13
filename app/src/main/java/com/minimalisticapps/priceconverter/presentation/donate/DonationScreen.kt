package com.minimalisticapps.priceconverter.presentation.donate

import android.app.Activity
import android.content.ActivityNotFoundException
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun DonationScreen() {
    val mContext = LocalContext.current as Activity

   Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp, start = 25.dp, end = 25.dp)
        ) {
            Text(
                text = "Thank you!",
                textAlign = TextAlign.Left,
                fontSize = 35.sp
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp, start = 25.dp, end = 25.dp)
        ) {
            Text(
                textAlign = TextAlign.Left,
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp, start = 25.dp, end = 25.dp)
        ) {
            Text(
                textAlign = TextAlign.Left,
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
                onClick = {
                    val link = "lightning:hank_milliken@stacker.news"
                    try {
                        mUriHandler.openUri(link)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(
                            mContext,
                            "No wallet supporting LNURL found.",
                            10f.toInt()
                        ).show()
                    }
                }) {
                Text(
                    text = "Donate⚡",
                    fontSize = 25.sp
                )
            }
        }
    }
}
