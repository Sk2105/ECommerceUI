package com.sgtech.ecommerceui.presentation.cart.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.sgtech.ecommerceui.MainViewModel
import com.sgtech.ecommerceui.data.product.ProductsItem
import com.sgtech.ecommerceui.model.api.product.state.ProductResult
import com.sgtech.ecommerceui.presentation.cart.screen.components.BottomBar
import com.sgtech.ecommerceui.presentation.cart.screen.components.TopBar
import com.sgtech.ecommerceui.presentation.navigation.routes.AppRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    mainViewModel: MainViewModel = MainViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState()
    )
    Scaffold(
        topBar = {
            TopBar(
                mainViewModel = mainViewModel,
                scrollBehavior = scrollBehavior,
                navHostController = navController
            )
        },
        bottomBar = {
            BottomBar(mainViewModel = mainViewModel, navHostController = navController)
        }
    ) { paddingValues ->
        CartContent(mainViewModel = mainViewModel, modifier = Modifier.padding(paddingValues)) {
            navController.navigate(AppRoute.Details.route + "/${it}")
        }


    }


}

@Composable
fun CartContent(mainViewModel: MainViewModel, modifier: Modifier, onClick: (Int) -> Unit = {}) {

    val products =
        (mainViewModel.uiState.value.productResult as ProductResult.Success).products.filter { product ->
            mainViewModel.cartList.find { it.id == product.id } != null
        }
    if (products.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Text(text = "Cart is Empty")
        }
        return
    }

    LazyColumn(modifier = modifier.fillMaxSize()) {

        products.forEach { product ->
            item {
                CartCard(
                    mainViewModel = mainViewModel,
                    product = product
                ) {
                    onClick(it)
                }
            }
        }
    }
}


@Composable
fun CartCard(
    mainViewModel: MainViewModel,
    product: ProductsItem = ProductsItem(
        id = 1,
        title = "title",
        category = "category",
        description = "description",
        image = "image",
        price = 10.0,
        rating = com.sgtech.ecommerceui.data.product.Rating(
            count = 10,
            rate = 10.0
        )
    ),
    onClick: (Int) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onClick(product.id)
            }
            .height(120.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Checkbox(
            checked = mainViewModel.cartList.find { it.id == product.id }?.isSelected?.value!!,
            onCheckedChange = {
                mainViewModel.cartList.find { it.id == product.id }?.isSelected!!.value = it
            })
        AsyncImage(
            model = product.image,
            contentDescription = "",
            modifier = Modifier
                .padding(8.dp)
                .size(100.dp)
                .clip(RoundedCornerShape(30.dp)),
            contentScale = androidx.compose.ui.layout.ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
                .weight(0.8f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = product.title,
                fontSize = 20.sp,
                maxLines = 2,
                softWrap = true,
                fontWeight = FontWeight.SemiBold
            )

            Text(text = product.category, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "$${product.price}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                IconButton(onClick = {
                    mainViewModel.cartList.remove(mainViewModel.cartList.find { it.id == product.id })
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = null,
                    )
                }
            }


        }

    }


}