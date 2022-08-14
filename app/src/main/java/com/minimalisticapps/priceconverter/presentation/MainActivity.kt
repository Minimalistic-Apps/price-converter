package com.minimalisticapps.priceconverter.presentation

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.minimalisticapps.priceconverter.common.utils.hideKeyboard
import com.minimalisticapps.priceconverter.common.utils.noRippleClickable
import com.minimalisticapps.priceconverter.presentation.currencylist.CoinListScreen
import com.minimalisticapps.priceconverter.presentation.donate.DonationScreen
import com.minimalisticapps.priceconverter.presentation.home.HomeScreen
import com.minimalisticapps.priceconverter.presentation.home.coinsStateValue
import com.minimalisticapps.priceconverter.presentation.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val mContext = LocalContext.current as Activity

            AppTheme(
                darkTheme = isSystemInDarkTheme()
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .noRippleClickable { hideKeyboard(mContext) },
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.HomeScreen.route
                        )
                        {
                            composable(route = Screen.HomeScreen.route) {
                                HomeScreen(navController = navController)
                            }
                            composable(route = Screen.CoinsListScreen.route) {
                                CoinListScreen(
                                    navController = navController,
                                    coinsState = coinsStateValue
                                )
                            }
                            composable(route = Screen.DonationScreen.route) {
                                DonationScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
