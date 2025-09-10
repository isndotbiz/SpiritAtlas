package com.spiritatlas.app.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Consent : Screen("consent")
}


