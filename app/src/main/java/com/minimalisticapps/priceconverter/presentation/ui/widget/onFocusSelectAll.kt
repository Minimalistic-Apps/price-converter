package com.minimalisticapps.priceconverter.presentation.ui.widget

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

fun Modifier.onFocusSelectAll(textFieldValueState: MutableState<TextFieldValue>): Modifier =
    composed {
        var triggerEffect by remember {
            mutableStateOf<Boolean?>(null)
        }
        if (triggerEffect != null) {
            LaunchedEffect(triggerEffect) {
                val tfv = textFieldValueState.value
                textFieldValueState.value = tfv.copy(selection = TextRange(0, tfv.text.length))
            }
        }
        Modifier.onFocusChanged { focusState ->
            if (focusState.isFocused) {
                triggerEffect = triggerEffect?.let { bool ->
                    !bool
                } ?: true
            }
        }
    }
