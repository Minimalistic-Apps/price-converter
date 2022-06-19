package com.minimalisticapps.priceconverter.presentation.home.viewmodels

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.minimalisticapps.priceconverter.common.utils.parseBigDecimalFromString
import kotlin.math.max
import kotlin.math.min

fun updateTextFieldModelWithCommas(
    original: TextFieldValue,
    new: TextFieldValue,
    formatFunction: (String) -> String,
): TextFieldValue {
    // when we deleted just a comma we want to move cursor
    val normalizedState = original.text.replace(",", "")
    val normalizedNewValue = new.text.replace(",", "")
    if (normalizedState == normalizedNewValue) {
        return TextFieldValue(
            text = original.text,
            selection = TextRange(
                max(0, new.selection.start),
                max(0, min(original.text.length, new.selection.end))
            )
        )
    }

    // Discard edit that are not valid number
    val isDot = new.text.trim() == "."
    val isValidNumberString = new.text.trim() == "" || parseBigDecimalFromString(new.text) != null
    val isNegative = new.text.contains('-')
    if (!isDot && (isNegative || !isValidNumberString)) {
        return original
    }

    // When actual number is changed, we have to adjust the selection
    // in case some comma was added
    val commasBefore = new.text.count { it == ',' }
    val formatted = formatFunction(new.text)
    val commasAfter = formatted.count { it == ',' }

    val diff = commasAfter - commasBefore

    return TextFieldValue(
        text = formatted,
        selection = TextRange(
            max(0, new.selection.start + diff),
            max(0, min(formatted.length, new.selection.end + diff))
        )
    )
}
