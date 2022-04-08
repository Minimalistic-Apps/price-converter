package com.minimalisticapps.priceconverter.presentation.ui.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ShowLinearIndicator() {
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
    )
}