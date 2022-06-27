package com.minimalisticapps.priceconverter.presentation.home.viewmodels

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.minimalisticapps.priceconverter.common.utils.formatBtcString
import org.junit.Assert.assertEquals
import org.junit.Test

class UpdateTextFieldModelWithFormattingTest {

    @Test
    fun updateTextFieldModelWithCommas_bitcoin() {
        data class Row(
            val label: String,
            val original: TextFieldValue,
            val new: TextFieldValue,
            val expected: TextFieldValue
        )

        val provider = listOf(
            Row(
                label = "just dot works",
                original = TextFieldValue(""),
                new = TextFieldValue(".", TextRange(start = 1, end = 1)),
                expected = TextFieldValue(".", TextRange(start = 1, end = 1)),
            ),
            Row(
                label = "when we deleted just a comma we want to move cursor",
                original = TextFieldValue("1.23,456,789", TextRange(start = 5, end = 5)),
                new = TextFieldValue("1.23456,789", TextRange(start = 4, end = 4)),
                expected = TextFieldValue("1.23,456,789", TextRange(start = 4, end = 4)),
            ),
            Row(
                label = "adding minus (-) does nothing",
                original = TextFieldValue("1.23,456,789", TextRange(start = 5, end = 5)),
                new = TextFieldValue("1.23,45-6,789", TextRange(start = 5, end = 5)),
                expected = TextFieldValue("1.23,456,789", TextRange(start = 5, end = 5)),
            ),
            Row(
                label = "adding minus (-) but valid number",
                original = TextFieldValue("1", TextRange(start = 0, end = 0)),
                new = TextFieldValue("-1", TextRange(start = 1, end = 1)),
                expected = TextFieldValue("1", TextRange(start = 0, end = 0)),
            ),
            Row(
                label = "Insert char before comma",
                original = TextFieldValue("1.23,456,789", TextRange(start = 4, end = 4)),
                new = TextFieldValue("1.230,456,789", TextRange(start = 4, end = 4)),
                expected = TextFieldValue("1.23,045,678", TextRange(start = 5, end = 5)),
            )
        )

        provider.forEach { row ->
            val (label, original, new, expected) = row
            println(label)
            val result = updateTextFieldModelWithCommas(original, new) { formatBtcString(it) }
            assertEquals(label, expected.toString(), result.toString())
        }

    }

}
