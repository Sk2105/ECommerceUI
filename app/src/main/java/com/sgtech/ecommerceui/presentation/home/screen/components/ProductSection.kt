package com.sgtech.ecommerceui.presentation.home.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddShoppingCart
import androidx.compose.material.icons.rounded.RemoveShoppingCart
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sgtech.ecommerceui.MainViewModel
import com.sgtech.ecommerceui.data.cart.Cart
import com.sgtech.ecommerceui.data.product.ProductsItem
import com.sgtech.ecommerceui.data.product.Rating
import com.sgtech.ecommerceui.model.api.product.state.ProductResult


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductSection(mainViewModel: MainViewModel, onClick: (Int) -> Unit = {}) {


    when (mainViewModel.uiState.value.productResult) {
        is ProductResult.Error -> {
            val error = (mainViewModel.uiState.value.productResult as ProductResult.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = error)
            }
        }

        ProductResult.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.Black)
            }

        }

        is ProductResult.Success -> {
            val products =
                (mainViewModel.uiState.value.productResult as ProductResult.Success).products.filter {
                    return@filter if (mainViewModel.constraint.value != "All") {
                        it.category == mainViewModel.constraint.value
                    } else {
                        true
                    }

                }
            FlowRow(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxSize(),
                maxItemsInEachRow = 2
            ) {
                products.forEach { product ->
                    ProductCard(product, mainViewModel) {
                        onClick(it)
                    }

                }

            }
        }
    }

}

@Composable
fun ProductCard(product: ProductsItem, mainViewModel: MainViewModel, onClick: (Int) -> Unit) {
    val screenWidth = LocalConfiguration.current.screenWidthDp / 2
    Column(modifier = Modifier
        .width(screenWidth.dp)
        .clickable {
            onClick(product.id)
        }
        .background(Color.Transparent, shape = RoundedCornerShape(30.dp))
        .wrapContentHeight()
        .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        Box(
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(30.dp)),
                contentScale = androidx.compose.ui.layout.ContentScale.FillBounds
            )

            IconButton(
                onClick = {
                    if (mainViewModel.cartList.find { it.id == product.id } != null) {
                        mainViewModel.cartList.removeIf { it.id == product.id }
                    } else {
                        mainViewModel.cartList.add(Cart(product.id))
                    }
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(40.dp)
                    .background(Color.White, shape = RoundedCornerShape(50.dp))
            ) {
                Icon(
                    imageVector = if (mainViewModel.cartList.find { it.id == product.id } != null) Icons.Rounded.RemoveShoppingCart else Icons.Rounded.AddShoppingCart,
                    contentDescription = "",
                    tint = Color.Black,
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        Text(
            text = product.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 8.dp),
            maxLines = 1,
            color = Color.Black.copy(0.8f),
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        )

        Text(
            text = "$${product.price}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 2.dp),
            maxLines = 1,
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp
        )


    }


}