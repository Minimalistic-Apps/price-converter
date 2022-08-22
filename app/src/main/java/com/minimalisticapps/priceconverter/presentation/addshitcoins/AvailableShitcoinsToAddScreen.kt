package com.minimalisticapps.priceconverter.presentation.addshitcoins

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.minimalisticapps.priceconverter.common.utils.ALLOWED_ISO_CURRENCIES
import com.minimalisticapps.priceconverter.common.utils.asShitcoinOnScreen
import com.minimalisticapps.priceconverter.common.utils.hideKeyboard
import com.minimalisticapps.priceconverter.data.repository.priceconverter.Shitcoin
import com.minimalisticapps.priceconverter.presentation.ui.item.ShitcoinListRow
import com.minimalisticapps.priceconverter.presentation.ui.widget.FilterInput

fun filterShitcoins(shitcoins: List<Shitcoin>, query: String): List<Shitcoin> {
    val lQuery = query.lowercase()

    return shitcoins.filter {

        val currency = ALLOWED_ISO_CURRENCIES[it.code]

        val nameMatch = it.name.lowercase().contains(lQuery)
        val codeMatch = it.code.lowercase().contains(lQuery)
        val territoryMatch = currency?.territories?.map { territory -> territory.name }
            ?.any { name -> name.lowercase().contains(lQuery) } == true

        nameMatch || codeMatch || territoryMatch
    }
}

@Composable
fun AvailableShitcoinsToAddScreen(
    navController: NavController,
    availableShitcoinsToAddScreenModel: AvailableShitcoinsToAddScreenModel = hiltViewModel(),
) {
    val mContext = LocalContext.current as Activity
    val shitcoins = availableShitcoinsToAddScreenModel.shitcoins.observeAsState()

    val query: MutableState<String> = remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            FilterInput(
                query = query.value,
                onValueChange = { text -> query.value = text }
            )
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filterShitcoins(shitcoins.value ?: listOf(), query.value)) { shitcoin ->
                    ShitcoinListRow(
                        shitcoin = shitcoin,
                        onClick = {
                            hideKeyboard(mContext)
                            availableShitcoinsToAddScreenModel.addShitcoinOnScreen(it.asShitcoinOnScreen())
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
