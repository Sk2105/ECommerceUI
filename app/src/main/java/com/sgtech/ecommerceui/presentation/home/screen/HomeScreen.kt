package com.sgtech.ecommerceui.presentation.home.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sgtech.ecommerceui.MainViewModel
import com.sgtech.ecommerceui.presentation.home.screen.components.CategorySection
import com.sgtech.ecommerceui.presentation.home.screen.components.HomeTopBar
import com.sgtech.ecommerceui.presentation.home.screen.components.ProductSection
import com.sgtech.ecommerceui.presentation.navigation.routes.AppRoute


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    mainViewModel: MainViewModel
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState()
    )

    Scaffold(
        topBar = {
            HomeTopBar(mainViewModel, scrollBehavior) {
                navHostController.navigate(AppRoute.Cart.route)
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { it ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            // Categories
            item {
                Text(
                    text = "Categories",
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .padding(vertical = 8.dp),
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = androidx.compose.ui.graphics.Color.Black
                )
            }

            item {
                CategorySection(mainViewModel)
            }


            // Products
            item {
                Text(
                    text = "Products",
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .padding(vertical = 8.dp),
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = androidx.compose.ui.graphics.Color.Black
                )
            }


            item {
                ProductSection(mainViewModel) {
                    navHostController.navigate(AppRoute.Details.route + "/${it}")

                }
            }


        }

    }
}