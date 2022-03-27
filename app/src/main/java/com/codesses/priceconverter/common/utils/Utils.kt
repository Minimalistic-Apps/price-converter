/*
 *
 * Created by Saad Iftikhar on 10/18/21, 5:19 PM
 * Copyright (c) 2021. All rights reserved
 *
 */

package com.codesses.priceconverter.common.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun <A : Activity> Activity.openActivity(activity: Class<A>) {
    Intent(this, activity).also {
        startActivity(it)
    }
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}


fun Activity.makeCall(phoneNo: String) {
    if (!TextUtils.isEmpty(phoneNo)) {

        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:$phoneNo")
        startActivity(Intent.createChooser(callIntent, "Make call..."))

    }
}


fun Activity.showToast(message: String) {

    Toast.makeText(this, message, Toast.LENGTH_SHORT)
        .show()

}

// Convert string to uri
fun String?.asUri(): Uri? {
    return try {
        Uri.parse(this)
    } catch (e: Exception) {
        null
    }
}

// Open uri in browser
fun Uri?.openInBrowser(context: Context) {
    this ?: return // Do nothing if uri is null

    val browserIntent = Intent(Intent.ACTION_VIEW, this)
    ContextCompat.startActivity(context, browserIntent, null)
}

fun hideKeyboard(context: Activity) {
    val inputManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = context.currentFocus
    if (view != null) {
        inputManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun getFormattedDate(inputDate: String): String {
    val originalFormat = SimpleDateFormat("d/M/y", Locale.US)
    val targetFormat = SimpleDateFormat("dd MMM yyyy", Locale.US)
    val finalDate = originalFormat.parse(inputDate)
    return targetFormat.format(finalDate)
}

fun Activity.isGalleryPermission(): Boolean {
    return if (ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 100
        )
        false
    } else true
}

fun View.preventDoubleClick() {
    Log.e("Base", "Double Click Called")
    isClickable = false
    Handler(Looper.getMainLooper()).postDelayed({ isClickable = true }, 1000L)
}

val String.toCapitalizeWord
    get() = this.lowercase()
        .split(" ")
        .joinToString(" ") { it.replaceFirstChar { it.uppercaseChar() } }

fun TextView.changeTextColor(color: Int) {
    this.setTextColor(ResourcesCompat.getColor(this.context.resources, color, null))
}

fun getView(id: Int, mContext: Context, parent: ViewGroup): View {
    return LayoutInflater.from(mContext)
        .inflate(id, parent, false)
}

fun CoroutineScope.launchPeriodicAsync(
    repeatMillis: Long,
    action: () -> Unit
) = this.async {
    if (repeatMillis > 0) {
        while (true) {
            action()
            delay(repeatMillis)
        }
    } else {
        action()
    }
}

fun Any.to8Precision() = BigDecimal(this.toString()).setScale(8, RoundingMode.HALF_EVEN)

//fun String.to8Precision() = BigDecimal(this.toDouble()).setScale(8, RoundingMode.HALF_EVEN)

fun Double.to3Precision() = BigDecimal(this).setScale(3, RoundingMode.HALF_EVEN)

fun String.to3Precision() = BigDecimal(this.toDouble()).setScale(3, RoundingMode.HALF_EVEN)

fun Double.toSatsFormat(): String {
    try {
        val splitNumber = this.toString().split(".")
        val afterDecimal = splitNumber.elementAtOrNull(1)
        return if (!afterDecimal.isNullOrEmpty()) {
            val formatter: NumberFormat =
                DecimalFormat("#,###", DecimalFormatSymbols(Locale.ENGLISH))

            "${splitNumber[0]}.${formatter.format(afterDecimal.toDouble())}"
        } else this.toString()
    } catch (ex: NumberFormatException) {
        ex.printStackTrace()
    }
    return ""
}

fun String.toSatsFormat(): String {
    try {
        val splitNumber = this.split(".")
        val afterDecimal = splitNumber.elementAtOrNull(1)
        return if (!afterDecimal.isNullOrEmpty()) {
            val formatter: NumberFormat =
                DecimalFormat("#,###", DecimalFormatSymbols(Locale.ENGLISH))

            "${splitNumber[0]}.${formatter.format(afterDecimal.toDouble())}"
        } else this
    } catch (ex: NumberFormatException) {
        ex.printStackTrace()
    }
    return ""
}


fun Double.toScientificNotation() = DecimalFormat("0.######E0").format(this)
fun String.toScientificNotation() = DecimalFormat("0.######E0").format(this.toDouble())


