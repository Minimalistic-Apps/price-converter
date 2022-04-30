package com.minimalisticapps.priceconverter.presentation

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.minimalisticapps.priceconverter.R
import com.minimalisticapps.priceconverter.presentation.coinlist.CoinListScreen
import com.minimalisticapps.priceconverter.presentation.home.HomeScreen
import com.minimalisticapps.priceconverter.presentation.home.coinsStateValue
import com.minimalisticapps.priceconverter.presentation.ui.theme.AppTheme
import com.minimalisticapps.priceconverter.presentation.ui.widget.SetTitle
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val mContext = LocalContext.current as Activity

            AppTheme(
                darkTheme = isSystemInDarkTheme()
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .height(60.dp)
                                .fillMaxWidth()
                                .background(
                                    Color(
                                        ContextCompat.getColor(
                                            mContext,
                                            R.color.color_app
                                        )
                                    )
                                )
                        ) {
                            Column(Modifier.align(Alignment.CenterVertically)) {
                                SetTitle(
                                    title = applicationContext.resources.getString(
                                        R.string.app_name
                                    )
                                )
                            }
                        }
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
                        }
                    }
                }
            }
        }
    }
}