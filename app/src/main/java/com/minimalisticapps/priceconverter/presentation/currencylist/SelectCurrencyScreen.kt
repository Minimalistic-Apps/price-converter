package com.minimalisticapps.priceconverter.presentation.currencylist

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.minimalisticapps.priceconverter.common.utils.hideKeyboard
import com.minimalisticapps.priceconverter.common.utils.toFiatCoinsExchange
import com.minimalisticapps.priceconverter.presentation.currencylist.viewmodels.CoinListViewModel
import com.minimalisticapps.priceconverter.presentation.states.CoinsState
import com.minimalisticapps.priceconverter.presentation.ui.item.CoinItem
import com.minimalisticapps.priceconverter.presentation.ui.widget.TextInput

@Composable
fun CoinListScreen(
    navController: NavController,
    coinListViewModel: CoinListViewModel = hiltViewModel(),
    coinsState: CoinsState
) {
    val mContext = LocalContext.current as Activity
    val coinsList = remember { mutableStateOf(coinsState.coins) }
    val searchText = remember { mutableStateOf("") }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            TextInput { text ->
                if (text.isNotEmpty()) {
                    coinsList.value = coinsState.coins.filter {
                        it.name.lowercase().contains(text.lowercase()) ||
                                it.code.lowercase().contains(text.lowercase())
                    }
                } else {
                    coinsList.value = coinsState.coins
                }
            }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(coinsList.value) { coin ->
                    CoinItem(
                        coin = coin,
                        onItemClick = { item ->
                            hideKeyboard(mContext)
                            coinListViewModel.insertFiatCoin(item.toFiatCoinsExchange())
                            navController.popBackStack()
                        })
                }
            }
        }

    }
}