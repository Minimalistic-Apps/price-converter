package com.minimalisticapps.priceconverter.presentation.ui.widget

import android.app.Activity
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minimalisticapps.priceconverter.R
import com.minimalisticapps.priceconverter.presentation.PriceConverterCornerShape
import com.minimalisticapps.priceconverter.presentation.ui.theme.FadedColorDark
import com.minimalisticapps.priceconverter.presentation.ui.theme.FadedColorLight

@Composable
fun FilterInput(query: String, onValueChange: (String) -> Unit) {
    val mContext = LocalContext.current as Activity

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)
            .padding(16.dp)
            .border(
                width = 0.5.dp,
                shape = PriceConverterCornerShape,
                brush = Brush.horizontalGradient(
                    listOf(MaterialTheme.colors.onBackground, MaterialTheme.colors.onBackground)
                )
            ),
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onBackground,
            backgroundColor = Color.Transparent,
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        value = query,
        onValueChange = onValueChange,
        singleLine = true,
        label = {
            Text(
                text = mContext.getString(R.string.search_currency),
                fontSize = 14.sp,
                color = if (isSystemInDarkTheme()) FadedColorDark else FadedColorLight
            )
        },
    )
}
