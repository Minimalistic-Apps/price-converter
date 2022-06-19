package com.minimalisticapps.priceconverter.presentation.ui.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minimalisticapps.priceconverter.R
import com.minimalisticapps.priceconverter.presentation.ui.theme.FadedColorDark
import com.minimalisticapps.priceconverter.presentation.ui.theme.FadedColorLight
import com.minimalisticapps.priceconverter.presentation.ui.widget.TextInputShitCoin
import com.minimalisticapps.priceconverter.presentation.ui.widget.formatUnitOfShitcoinPrice
import java.math.BigDecimal

@Composable
fun ItemFiatCoin(
    index: Int,
    code: String,
    oneUnitOfShitcoinInBTC: BigDecimal?,
    state: MutableState<TextFieldValue>,
    onValueChange: (TextFieldValue) -> Unit,
    onLongPress: () -> Unit,
    onDeleteClick: (index: Int) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            onLongPress()
                        }
                    )
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3.0f)
            ) {
                TextInputShitCoin(state, onValueChange)
            }

            Text(
                fontSize = 18.sp,
                text = code,
                modifier = Modifier
                    .padding(start = 25.dp)
                    .width(45.dp)
            )

            Image(
                painterResource(R.drawable.ic_delete),
                "content description",
                modifier = Modifier
                    .padding(start = 0.dp, end = 15.dp)
                    .clickable { onDeleteClick(index) }
            )
        }
        val unitOfShitcoinInBtcFormatted =
            formatUnitOfShitcoinPrice(oneUnitOfShitcoinInBTC)
        Text(
            color = if (isSystemInDarkTheme()) FadedColorDark else FadedColorLight,
            fontFamily = FontFamily.Monospace,
            text = "1 $code = $unitOfShitcoinInBtcFormatted BTC",
            style = MaterialTheme.typography.body1,
            fontSize = 13.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 1.dp, horizontal = 40.dp)
        )
    }
}
