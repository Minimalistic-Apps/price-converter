package com.minimalisticapps.priceconverter

import java.util.*

fun getDateFromTimestampInMillis(timestamp: Long): Date {
    val calendar = Calendar.getInstance(Locale.ENGLISH)
    calendar.timeInMillis = timestamp

    return calendar.time
}