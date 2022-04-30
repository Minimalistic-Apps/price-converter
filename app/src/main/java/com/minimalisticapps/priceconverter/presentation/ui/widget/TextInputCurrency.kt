package com.minimalisticapps.priceconverter.presentation.ui.widget

import android.app.Activity
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.minimalisticapps.priceconverter.R

@Composable
fun TextInput(
    onValueChange: (String) -> Unit,
) {
    val searchText = remember {
        mutableStateOf("")
    }
    val mContext = LocalContext.current as Activity

    TextField(
        modifier =
        Modifier
            .fillMaxWidth()
            .height(85.dp)
            .padding(16.dp)
            .border(
                width = 0.5.dp,
                shape = CircleShape,
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
        value = searchText.value,
        onValueChange = { text ->
            searchText.value = text
            onValueChange(text)
        },
        singleLine = true,
        label = {
            Text(
                text = mContext.getString(R.string.search_currency),
                fontSize = 14.sp,
                color = Color(ContextCompat.getColor(mContext, R.color.color_grey_ce))
            )
        }
    )
}

