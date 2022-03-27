package com.codesses.priceconverter.presentation

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
    object AboutScreen: Screen("about_screen")
}
