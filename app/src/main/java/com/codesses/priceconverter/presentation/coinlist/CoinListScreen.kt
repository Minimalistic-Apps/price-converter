package com.codesses.priceconverter.presentation.coinlist

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.codesses.priceconverter.R
import com.codesses.priceconverter.common.enums.EnumThemeModes
import com.codesses.priceconverter.common.models.ThemeData
import com.codesses.priceconverter.common.utils.to8Precision
import com.codesses.priceconverter.presentation.ui.theme.AppTheme
import com.codesses.priceconverter.presentation.ui.viewmodel.PriceConverterViewModel
import com.codesses.priceconverter.presentation.ui.widget.SetTitle
import com.codesses.priceconverter.presentation.ui.widget.ShowError
import com.codesses.priceconverter.presentation.ui.widget.ShowProgressDialog
import com.codesses.priceconverter.presentation.ui.widget.TurnOnDarkMode
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.util.*

@Composable
fun ShowCoinsList(
    navController: NavController,
    priceConverterViewModel: PriceConverterViewModel = hiltViewModel()
) {
    val mContext = LocalContext.current
    val mode = remember { mutableStateOf(ThemeData(EnumThemeModes.LIGHT.value, false)) }

    val coinsState = priceConverterViewModel.state.value
    val isRefreshing by priceConverterViewModel.isRefreshing.observeAsState()
    AppTheme(
        darkTheme = mode.value.isEnable,
        content = {
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
                        text = { Text(text = "") },
                        onClick = {
                            val name = UUID.randomUUID().toString()

                        }
                    )

                },
                content = {

                    Column {
                        Row(
                            modifier = Modifier
                                .height(60.dp)
                                .fillMaxWidth()
                                .background(MaterialTheme.colors.background)
                        ) {
                            Column(Modifier.align(Alignment.CenterVertically)) {
                                SetTitle(
                                    title = mContext.resources.getString(
                                        R.string.price_converter
                                    )
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.End
                            ) { TurnOnDarkMode(mode) }
                        }
                        val number = "1.00000000087567858780".to8Precision()
                        Log.d("Decimal_Coin", "ShowCoinsList: $number")
                        Spacer(modifier = Modifier.size(16.dp))
                        SwipeRefresh(state = rememberSwipeRefreshState(
                            isRefreshing = isRefreshing
                                ?: false
                        ), onRefresh = {
                            priceConverterViewModel.refreshData()
                        }) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    items(
                                        items = coinsState.coins ?: ArrayList()
                                    ) {
                                        Log.e("Values", coinsState.coins.toString())
                                    }
                                }
                                if (coinsState.error.isNotBlank()) {
                                    ShowError(coinsState.error)
                                }
                                if (coinsState.isLoading) {
                                    ShowProgressDialog()
                                }
                            }
                        }

                    }
                })
        })

}