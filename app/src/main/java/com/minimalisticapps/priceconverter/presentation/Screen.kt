package com.minimalisticapps.priceconverter.presentation

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object AvailableShitcoinsToAdd : Screen("available_shitcoins_to_add")
    object DonationScreen : Screen("donation_screen")
}
