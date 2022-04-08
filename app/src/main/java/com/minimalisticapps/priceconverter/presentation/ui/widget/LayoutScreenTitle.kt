package com.minimalisticapps.priceconverter.presentation.ui.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SetTitle(title: String) {

    Row(
        modifier = Modifier.padding(
            start = 20.dp,
            top = 10.dp,
            end = 20.dp,
            bottom = 10.dp
        )
    ) {
        Text(
            textAlign = TextAlign.Start,
            text = title,
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
    }
}