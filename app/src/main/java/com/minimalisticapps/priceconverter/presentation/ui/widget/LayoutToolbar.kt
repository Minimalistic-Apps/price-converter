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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minimalisticapps.priceconverter.R

@Composable
fun SetToolbar(
    title: String,
    onReload: () -> Unit
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