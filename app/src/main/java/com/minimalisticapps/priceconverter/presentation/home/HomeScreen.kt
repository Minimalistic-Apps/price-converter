package com.minimalisticapps.priceconverter.presentation.home

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.minimalisticapps.priceconverter.R
import com.minimalisticapps.priceconverter.common.utils.showToast
import com.minimalisticapps.priceconverter.presentation.Screen
import com.minimalisticapps.priceconverter.presentation.home.viewmodels.HomeViewModel
import com.minimalisticapps.priceconverter.presentation.states.CoinsState
import com.minimalisticapps.priceconverter.presentation.ui.widget.*
import com.minimalisticapps.priceconverter.room.entities.BitPayCoinWithFiatCoin
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

var coinsStateValue: CoinsState = CoinsState()

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val mContext = LocalContext.current as Activity

    //    states
    val coinsState = homeViewModel.state.value
    val timeAgo = homeViewModel.timeAgoState.value
    val isDiffLongerThat1hours = homeViewModel.isDiffLongerThat1hours.value
    val isRefreshing by homeViewModel.isRefreshing.observeAsState()
    val color = if (isDiffLongerThat1hours) Color.Red else Color.Green
    val fiatCoinsListState = homeViewModel.fiatCoinsListState.value
    val isErrorShown = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                backgroundColor = Color.Red,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "image",
                        tint = Color.White
                    )
                },
                text = {
                    Text(
                        text = mContext.resources.getString(R.string.add),
                        color = Color.White
                    )
                },
                onClick = {
                    coinsStateValue = coinsState
                    navController.navigate(Screen.CoinsListScreen.route)
                }
            )

        }
    ) {
        SwipeRefresh(state = rememberSwipeRefreshState(
            isRefreshing = isRefreshing
                ?: false
        ), onRefresh = {
            homeViewModel.refreshData()
            isErrorShown.value = false
        }) {
            Column(modifier = Modifier.fillMaxSize()) {
                if (coinsState.isLoading && isRefreshing == true) {
                    ShowLinearIndicator()
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Text(
                        text = timeAgo,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier
                            .padding(horizontal = 10.dp),
                        color = color,
                        textAlign = TextAlign.Left
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.width(250.dp)) {
                        TextInputBtc(onValueChange = {
                            homeViewModel.getFiatCoins()
                        })
                    }

                    Text(
                        text = "BTC",
                        textAlign = TextAlign.Start,
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 10.dp)
                ) {
                    items(
                        items = fiatCoinsListState
                    ) { pair ->
                        FiatCoinItem(
                            pair = pair,
                            onLongPress = {
                                homeViewModel.deleteFiatCoin(fiatCoinExchange = it)
                            },
                            onValueChanged = object : (BitPayCoinWithFiatCoin, Double) -> Unit {
                                override fun invoke(
                                    bitPayCoinWithFiatCoin: BitPayCoinWithFiatCoin,
                                    value: Double
                                ) {
                                    if (value != 0.toDouble()) {
                                        homeViewModel.setTextFieldValueBtc(
                                            bitPayCoinWithFiatCoin
                                                .bitPayExchangeRate
                                                .oneShitCoinValue
                                                .times(value)
                                                .toString(),
                                            true
                                        )

                                        homeViewModel.getFiatCoins()
                                    }
                                }
                            }
                        )
                    }
                }
            }

            if (coinsState.error.isNotBlank() && !isErrorShown.value) {
                mContext.showToast(coinsState.error)
                isErrorShown.value = true
            }
            if (coinsState.isLoading && isRefreshing == false)
                ShowProgressDialog()

        }

    }

}

