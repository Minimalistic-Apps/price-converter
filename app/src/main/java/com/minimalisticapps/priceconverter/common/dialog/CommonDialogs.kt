package com.minimalisticapps.priceconverter.common.dialog

import android.app.Activity
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
import com.minimalisticapps.priceconverter.R

@Composable
fun ShowProgressDialog() {
    Box(
        modifier = Modifier.fillMaxSize()
    )
    {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }

}

@Composable
fun ConfirmationDialog(
    onPositiveClick: (value: Boolean) -> Unit,
    onNegativeClick: (value: Boolean) -> Unit
) {
    val mContext = LocalContext.current as Activity
    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            TextButton(onClick = { onPositiveClick(false) })
            { Text(text = mContext.getString(R.string.yes)) }
        },
        dismissButton = {
            TextButton(onClick = { onNegativeClick(false) })
            { Text(text = mContext.getString(R.string.no)) }
        },
        text = { Text(text = mContext.getString(R.string.confirmation_dialog_desc)) }
    )
}