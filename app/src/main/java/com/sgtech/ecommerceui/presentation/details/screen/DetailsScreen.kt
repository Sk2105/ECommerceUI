package com.sgtech.ecommerceui.presentation.details.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.sgtech.ecommerceui.MainViewModel
import com.sgtech.ecommerceui.data.product.ProductsItem
import com.sgtech.ecommerceui.model.api.product.state.ProductDetailResult
import com.sgtech.ecommerceui.presentation.details.screen.components.BottomBar
import com.sgtech.ecommerceui.presentation.details.screen.components.TopBar
import com.sgtech.ecommerceui.presentation.home.screen.components.ProductSection
import com.sgtech.ecommerceui.presentation.navigation.routes.AppRoute


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    mainViewModel: MainViewModel,
    navHostController: NavHostController = rememberNavController(),
    productId: Int = 1
) {
    LaunchedEffect(key1 = navHostController) {
        Log.d("ProductId", productId.toString())
        mainViewModel.fetchProductById(productId)
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState()
    )

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                mainViewModel = mainViewModel,
                scrollBehavior = scrollBehavior,
                navHostController = navHostController
            )
        },
        bottomBar = {
            BottomBar(
                mainViewModel = mainViewModel,
                productId = productId,
                navHostController = navHostController
            )
        }
    ) { paddingValues ->


        when (mainViewModel.productUiState.value.productDetailResult) {
            is ProductDetailResult.Error -> {
                val error =
                    (mainViewModel.productUiState.value.productDetailResult as ProductDetailResult.Error).message
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = error)
                }
            }

            ProductDetailResult.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
                {
                    CircularProgressIndicator(color = Color.Black)
                }
            }

            is ProductDetailResult.Success -> {
                val product =
                    (mainViewModel.productUiState.value.productDetailResult as ProductDetailResult.Success).products
                mainViewModel.constraint.value = product.category
                DetailsContent(
                    mainViewModel = mainViewModel,
                    modifier = Modifier.padding(paddingValues),
                    product = product,
                    navHostController = navHostController
                )
            }
        }

    }

}

@Composable
fun DetailsContent(
    mainViewModel: MainViewModel,
    navHostController: NavHostController,
    modifier: Modifier,
    product: ProductsItem
) {
    LazyColumn(modifier = modifier) {
        item {
            AsyncImage(
                model = product.image,
                contentDescription = "",
                modifier = Modifier
                    .padding(8.dp)
                    .height(400.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .fillMaxWidth(),
                contentScale = androidx.compose.ui.layout.ContentScale.FillBounds
            )
        }

        item {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = product.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp),
                    maxLines = 2,
                    lineHeight = 24.sp,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )

                LazyRow(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth()
                ) {
                    items(product.rating.rate.toInt())
                    {
                        Icon(
                            imageVector = Icons.Rounded.Star,
                            contentDescription = "",
                            tint = Color(0xFFD3B100),
                            modifier = Modifier
                                .size(24.dp)

                        )
                    }
                    item {
                        Text(text = "(${product.rating.count})", fontSize = 18.sp)

                    }
                }
                Text(
                    text = "Category: ${product.category}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = "Description",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(text = product.description, fontSize = 18.sp)


            }
        }

        item {
            Text(
                text = "Similar Products",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            )
        }

        item {
            SimilarProductsSection(
                mainViewModel = mainViewModel,
                navHostController = navHostController
            )

        }


    }

}

@Composable
fun SimilarProductsSection(mainViewModel: MainViewModel, navHostController: NavHostController) {

    when (mainViewModel.productUiState.value.productDetailResult) {
        is ProductDetailResult.Error -> {
            val error =
                (mainViewModel.productUiState.value.productDetailResult as ProductDetailResult.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = error)
            }
        }

        ProductDetailResult.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
            {
                CircularProgressIndicator(color = Color.Black)
            }
        }

        is ProductDetailResult.Success -> {
            val products =
                (mainViewModel.productUiState.value.productDetailResult as ProductDetailResult.Success).products
            ProductSection(mainViewModel = mainViewModel) {
                navHostController.navigate(AppRoute.Details.route + "/${it}")

            }
        }
    }

}