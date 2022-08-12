package com.minimalisticapps.priceconverter.presentation.donate

import android.app.Activity
import android.content.ActivityNotFoundException
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                        append("Free Open-Source Privacy Respecting Minimalistic")
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


        if (donationViewModel.isLoading.value) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircularProgressIndicator()
            }

        } else if (donationViewModel.lnUrl.observeAsState().value == null) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { donationViewModel.userClickedDonate() }) {
                    Text(text = "Donate⚡", fontSize = 25.sp)
                }
            }

        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 25.dp, start = 25.dp, end = 25.dp)
            ) {
                val link = "lightning://${donationViewModel.lnUrl.value}"
                val mUriHandler = LocalUriHandler.current

                Button(onClick = {
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
                    Text(text = "Pay⚡", fontSize = 25.sp)
                }
            }
        }
    }
}
