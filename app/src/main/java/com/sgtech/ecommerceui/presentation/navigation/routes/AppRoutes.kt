package com.sgtech.ecommerceui.presentation.navigation.routes

sealed class AppRoute(val route: String) {
    data object Home : AppRoute("home")
    data object Details : AppRoute("details")
    data object Cart : AppRoute("cart")
    data object Search : AppRoute("search")
}