package com.minimalisticapps.priceconverter.presentation

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object CoinsListScreen : Screen("coins_list_screen")
    object DonationScreen : Screen("donation_screen")
}
