package com.codesses.priceconverter.presentation.ui.widget

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codesses.priceconverter.common.models.ThemeData
import com.codesses.priceconverter.R
import com.codesses.priceconverter.common.enums.EnumThemeModes


@Composable
fun EnableDarkMode(mode: MutableState<ThemeData>) {
    val isDarkTheme = isSystemInDarkTheme()
    Row {

        /** Light theme **/
        Spacer(modifier = Modifier.size(16.dp))
        RadioButton(
            selected = mode.value.title == EnumThemeModes.LIGHT.value,
            onClick = {
                mode.value = ThemeData(EnumThemeModes.LIGHT.value, false)
            })
        Text(
            modifier = Modifier.align(CenterVertically),
            text = EnumThemeModes.LIGHT.value,
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.Bold
        )

        /** Dark theme **/
        Spacer(modifier = Modifier.size(16.dp))
        RadioButton(
            selected = mode.value.title == EnumThemeModes.DARK.value,
            onClick = {
                mode.value = ThemeData(EnumThemeModes.DARK.value, true)
            }
        )
        Text(
            modifier = Modifier.align(CenterVertically),
            text = EnumThemeModes.DARK.value,
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.Bold
        )

        /** System theme **/
        Spacer(modifier = Modifier.size(16.dp))
        RadioButton(
            selected = mode.value.title == EnumThemeModes.SYSTEM.value,
            onClick = {
                mode.value = ThemeData(EnumThemeModes.SYSTEM.value, isDarkTheme)
            })
        Text(
            modifier = Modifier.align(CenterVertically),
            text = EnumThemeModes.SYSTEM.value,
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TurnOnDarkMode(mode: MutableState<ThemeData>) {
    val isDarkTheme = isSystemInDarkTheme()

    Row {
        Text(
            modifier = Modifier.align(CenterVertically),
            text = LocalContext.current.resources.getString(R.string.dark_mode),
            color = MaterialTheme.colors.onBackground,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.size(16.dp))
        Switch(
//        colors = SwitchDefaults.colors(
//            checkedThumbColor = Color.DarkGray,
//            uncheckedThumbColor = Color.DarkGray,
//            checkedTrackColor = Color.Blue,
//            uncheckedTrackColor = Color.Blue,
//            checkedTrackAlpha = 1.0f,
//            uncheckedTrackAlpha = 1.0f
//        ),
            modifier = Modifier.padding(10.dp, 0.dp),
            checked = mode.value.isEnable,
            onCheckedChange = {
                mode.value = ThemeData(EnumThemeModes.DARK.value, it)
            }
        )
    }
}