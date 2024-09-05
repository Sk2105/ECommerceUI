package com.sgtech.ecommerceui.presentation.cart.screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sgtech.ecommerceui.MainViewModel
import com.sgtech.ecommerceui.presentation.navigation.routes.AppRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    mainViewModel: MainViewModel,
    scrollBehavior: TopAppBarScrollBehavior,
    navHostController: NavHostController = rememberNavController()
) {

    TopAppBar(
        scrollBehavior = scrollBehavior,
        windowInsets = WindowInsets(top = 0),
        modifier = Modifier.padding(top = 16.dp),
        title = {
            Text(
                text = "Cart",
                color = Color.Black,
                modifier = Modifier.padding(start = 8.dp),
                maxLines = 1,
                softWrap = false,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                mainViewModel.constraint.value = "All"
                navHostController.popBackStack()
            }) {
                Icon(Icons.Rounded.ArrowBack, contentDescription = "Localized description")
            }
        },

        )

}