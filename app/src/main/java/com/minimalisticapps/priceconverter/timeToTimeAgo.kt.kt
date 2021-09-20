package com.minimalisticapps.priceconverter

import java.util.*
import java.util.concurrent.TimeUnit

fun isDiffLongerThat1hours(past: Date?, now: Date): Boolean {
    if (past == null) {
        return false
    }

    return TimeUnit.MILLISECONDS.toHours(now.time - past.time) > 20
}

fun timeToTimeAgo(past: Date?, now: Date): String {
    if (past == null) {
        return ""
    }

    val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
    val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
    val hours: Long = TimeUnit.MILLISECONDS.toHours(now.time - past.time)
    val days: Long = TimeUnit.MILLISECONDS.toDays(now.time - past.time)

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