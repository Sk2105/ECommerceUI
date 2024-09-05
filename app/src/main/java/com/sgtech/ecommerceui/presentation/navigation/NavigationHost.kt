package com.sgtech.ecommerceui.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sgtech.ecommerceui.MainViewModel
import com.sgtech.ecommerceui.presentation.cart.screen.CartScreen
import com.sgtech.ecommerceui.presentation.details.screen.DetailsScreen
import com.sgtech.ecommerceui.presentation.home.screen.HomeScreen
import com.sgtech.ecommerceui.presentation.navigation.routes.AppRoute
import com.sgtech.ecommerceui.presentation.search.screen.SearchScreen

@Composable
fun NavigationHost(
    navHostController: NavHostController = rememberNavController(),
    mainViewModel: MainViewModel
) {
    NavHost(navController = navHostController, startDestination = AppRoute.Home.route) {
        composable(AppRoute.Home.route) {
            mainViewModel.constraint.value = "All"
            HomeScreen(navHostController, mainViewModel)
        }
        composable(AppRoute.Cart.route) {
            CartScreen(mainViewModel,navHostController)
        }

        composable(AppRoute.Search.route) {
            SearchScreen(navHostController)
        }
        composable(AppRoute.Details.route + "/{productId}") {
            val productId = it.arguments?.getString("productId")?.toInt()
            DetailsScreen(
                mainViewModel = mainViewModel,
                navHostController = navHostController,
                productId = productId ?: 1
            )
        }
    }

}