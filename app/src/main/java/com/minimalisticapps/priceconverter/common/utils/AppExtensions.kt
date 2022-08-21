package com.minimalisticapps.priceconverter.common.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.minimalisticapps.priceconverter.common.utils.AppConstants.BIT_COIN_PRECISION
import com.minimalisticapps.priceconverter.data.repository.priceconverter.CurrencyRate
import com.minimalisticapps.priceconverter.room.entities.ScreenCurrencyRecord
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*
import java.util.concurrent.TimeUnit


fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Long.isDiffLongerThat1hours(past: Long): Boolean {
    return TimeUnit.MILLISECONDS.toHours(this - past) > 1
}

fun Long.timeToTimeAgo(past: Long): String {
    if (past == 0L) {
        return ""
    }

    val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(this - past)
    val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(this - past)
    val hours: Long = TimeUnit.MILLISECONDS.toHours(this - past)
    val days: Long = TimeUnit.MILLISECONDS.toDays(this - past)

    return when {
        seconds < 60 -> {
            "Rates updated $seconds second${if (seconds > 1) "s" else ""} ago"
        }
        minutes < 60 -> {
            "Rates updated $minutes minute${if (minutes > 1) "s" else ""} ago"
        }
        hours < 24 -> {
            "Rates updated $hours hour${if (hours > 1) "s" else ""} ago"
        }
        else -> {
            "Rates updated $days day${if (days > 1) "s" else ""} ago"
        }
    }
}

fun CurrencyRate.toFiatCoinsExchange(): ScreenCurrencyRecord {
    return ScreenCurrencyRecord(name = name, code = code)
}

fun String.toSatsFormat(): String {
    try {
        return if (this.isNotEmpty()) {
            val format: NumberFormat =
                DecimalFormat("#.###", DecimalFormatSymbols(Locale.ENGLISH))
            format.format(this.toDouble())
        } else this
    } catch (ex: NumberFormatException) {
        ex.printStackTrace()
    }
    return ""
}

fun String.to8Decimal(): String {
    if (this.isEmpty()) {
        return ""
    }
    val splitNo = this.split("E")
    val eightDecimal: Double = String.format("%.8f", splitNo[0].toDouble()).toDouble()
    return "${eightDecimal}E${splitNo[1]}"
}

fun formatBtc(value: BigDecimal?): String {
    if (value == null) {
        return ""
    }

    return formatBtcString(value.toPlainString())
}

fun formatBtcString(input: String): String {
    if (input.trim() == "." || input.trim() == "") {
        return input
    }

    val parts = input.replace(",", "").split('.')
    val beforeDot = parts[0].reversed().chunked(3).joinToString(",").reversed()
    val afterDot =
        if (parts.size > 1) if (parts[1].length > 8) parts[1].substring(0, 8) else parts[1] else ""

    var formattedAfterDot = ""
    var depth = 0

    for (ch in afterDot) {
        if (depth >= BIT_COIN_PRECISION) {
            break
        }

        if (depth == 2 || (depth - 2) % 3 == 0) {
            formattedAfterDot += ","
        }
        depth++

        formattedAfterDot += ch
    }

    return beforeDot + (if (formattedAfterDot.isNotEmpty() || input.contains('.')) ".$formattedAfterDot" else "")
}

const val SHITCOIN_PRECISION = 3

const val COMMA_SEPARATORS_DISTANCE = 3

val SATS_IN_BTC = BigDecimal("100000000")

fun formatNumberString(input: String, precision: Int): String {
    if (input.trim() == "." || input.trim() == "") {
        return input
    }

    val parts = input.replace(",", "").split('.')
    val beforeDot =
        parts[0].reversed().chunked(COMMA_SEPARATORS_DISTANCE).joinToString(",").reversed()
    val afterDot =
        if (parts.size > 1) if (parts[1].length > precision) parts[1].substring(
            0,
            precision
        ) else parts[1] else ""

    return beforeDot + (if (afterDot.isNotEmpty() || input.contains('.')) ".$afterDot" else "")
}

fun formatSats(input: BigDecimal): String {
    return formatSatsString(input.toPlainString())
}

fun formatSatsString(input: String): String {
    return formatNumberString(input, 3)
}

fun formatFiatShitcoin(input: BigDecimal): String {
    return formatNumberString(input.toPlainString(), SHITCOIN_PRECISION)
}

fun parseBigDecimalFromString(input: String): BigDecimal? {
    val strippedOfCommas = input.replace(",", "")
    return try {
        BigDecimal(strippedOfCommas)
    } catch (e: NumberFormatException) {
        null
    }
}

fun hideKeyboard(context: Activity) {
    val inputManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = context.currentFocus
    if (view != null) {
        inputManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}
