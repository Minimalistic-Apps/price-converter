package com.minimalisticapps.priceconverter.common.dialog

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.DialogProperties
import com.minimalisticapps.priceconverter.R

@Composable
fun ConfirmationDialog(
    onPositiveClick: (value: Boolean) -> Unit,
    onDismiss: (value: Boolean) -> Unit
) {
    val mContext = LocalContext.current as Activity
    AlertDialog(
        onDismissRequest = {
            onDismiss(false)
        },
        confirmButton = {
            TextButton(onClick = { onPositiveClick(false) })
            { Text(text = mContext.getString(R.string.yes)) }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss(false) })
            { Text(text = mContext.getString(R.string.no)) }
        },
        text = { Text(text = mContext.getString(R.string.confirmation_dialog_desc)) },
    )
}
