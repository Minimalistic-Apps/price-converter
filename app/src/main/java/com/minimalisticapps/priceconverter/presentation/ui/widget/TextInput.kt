package com.minimalisticapps.priceconverter.presentation.ui.widget

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TextInput(
    onValueChange: (String) -> Unit,
) {
    val searchText = remember {
        mutableStateOf("")
    }
    TextField(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(10.dp),
                brush = Brush.horizontalGradient(
                    listOf(Color.Black, Color.Black)
                )),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Gray,
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
        placeholder = { Text("Search Currency") },

        )
}

