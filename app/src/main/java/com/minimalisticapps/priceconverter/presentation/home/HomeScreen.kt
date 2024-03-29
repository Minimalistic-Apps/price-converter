package com.minimalisticapps.priceconverter.presentation.home

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.minimalisticapps.priceconverter.R
import com.minimalisticapps.priceconverter.common.dialog.ConfirmationDialog
import com.minimalisticapps.priceconverter.common.utils.PCSharedStorage
import com.minimalisticapps.priceconverter.common.utils.showToast
import com.minimalisticapps.priceconverter.common.utils.asShitcoinOnScreen
import com.minimalisticapps.priceconverter.presentation.Screen
import com.minimalisticapps.priceconverter.presentation.states.CoinsState
import com.minimalisticapps.priceconverter.presentation.ui.item.ItemFiatCoin
import com.minimalisticapps.priceconverter.presentation.ui.theme.ErrorColor
import com.minimalisticapps.priceconverter.presentation.ui.theme.PrimaryColor
import com.minimalisticapps.priceconverter.presentation.ui.theme.SecondaryColorForDark
import com.minimalisticapps.priceconverter.presentation.ui.widget.SetToolbar
import com.minimalisticapps.priceconverter.presentation.ui.widget.ShowLinearIndicator
import com.minimalisticapps.priceconverter.presentation.ui.widget.TextInputBtc

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
    val isLongerThan1hour = homeViewModel.isLongerThan1hour.value
    val isRefreshing = homeViewModel.isRefreshing.value
    val swipeRefreshState = rememberSwipeRefreshState(false)
    val colorTimeAgo = if (isLongerThan1hour) ErrorColor else SecondaryColorForDark
    val fiatCoinsListState = homeViewModel.shitcoinListState.value
    val isErrorShown = remember { mutableStateOf(false) }
    val isShownConfirmDialog = remember { mutableStateOf(false) }
    val selectedFiatCoin = remember { mutableStateOf<Int?>(null) }

    if (coinsState.error.isNotBlank() && !isErrorShown.value) {
        mContext.showToast(coinsState.error)
        isErrorShown.value = true
    }

    // Default usd currency added once app launch
    if (!PCSharedStorage.isUsdAddDefault()) {
        val usdCurrency = coinsState.coins.filter {
            it.name.lowercase().contains("US Dollar") ||
                    it.code.lowercase().contains("USD".lowercase())
        }.elementAtOrNull(0)
        usdCurrency?.let {
            homeViewModel.insertFiatCoin(it.asShitcoinOnScreen())
            PCSharedStorage.saveUsdAsDefault(true)
        }
    }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { homeViewModel.refreshData() },
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    backgroundColor = PrimaryColor,
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
                        navController.navigate(Screen.AvailableShitcoinsToAdd.route)
                    }
                )

                if (isShownConfirmDialog.value) {
                    ConfirmationDialog(
                        onPositiveClick = {
                            isShownConfirmDialog.value = it
                            homeViewModel.deleteFiatCoin(index = selectedFiatCoin.value)
                        },
                        onDismiss = {
                            isShownConfirmDialog.value = it
                        })
                }

            }
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth()
                        .background(PrimaryColor)
                ) {
                    SetToolbar(
                        title = mContext.resources.getString(R.string.app_name),
                        onReload = {
                            homeViewModel.refreshData()
                            isErrorShown.value = false
                        },
                        onDonateClick = {
                            navController.navigate(Screen.DonationScreen.route)
                        },
                        onBtcOrSatsChange = { homeViewModel.switchBtcOrSats() },
                        btcOrSats = homeViewModel.btcOrSats.value,
                        donationToken = PCSharedStorage.getDonationToken()
                    )
                }

                if (coinsState.isLoading && isRefreshing) {
                    ShowLinearIndicator()
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 0.dp),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Text(
                        text = timeAgo,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier
                            .padding(vertical = 10.dp, horizontal = 16.dp),
                        color = colorTimeAgo,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Left
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 0.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(3.0f)
                            ) {
                                TextInputBtc()
                            }

                            Text(
                                text = if (homeViewModel.btcOrSats.value == "BTC") "BTC" else "Sats",
                                textAlign = TextAlign.Start,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(start = 11.dp, end = 50.dp)
                                    .width(45.dp)
                            )
                        }
                    }
                    items(
                        items = fiatCoinsListState,
                        key = { pair -> pair.first }
                    ) { pair ->
                        val code = pair.second.shitcoinOnScreen.code
                        val state = homeViewModel.shitcoinInputsState[code]!!

                        ItemFiatCoin(
                            index = pair.first,
                            code = code,
                            oneUnitOfShitcoinInBTC = pair.second.shitcoin.satsPerUnit,
                            state = state,
                            onValueChange = { homeViewModel.updateShitcoin(pair.first, it) },
                            onLongPress = { // Todo: ordering
                            },
                            onSelected = {
                                homeViewModel.selectedCoin.value = code
                            },
                            onDeleteClick = {
                                selectedFiatCoin.value = it
                                isShownConfirmDialog.value = true
                            },
                            btcOrSats = homeViewModel.btcOrSats.value
                        )
                    }
                }
            }
        }
    }
}

