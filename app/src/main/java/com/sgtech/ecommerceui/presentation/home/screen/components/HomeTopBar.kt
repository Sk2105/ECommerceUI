package com.sgtech.ecommerceui.presentation.home.screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingBag
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.icons.sharp.Search
import androidx.compose.material.icons.sharp.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.sgtech.ecommerceui.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HomeTopBar(
    mainViewModel: MainViewModel = MainViewModel(),
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState()
    ),
    onClick: () -> Unit = {}
) {
    TopAppBar(scrollBehavior = scrollBehavior,
        windowInsets = WindowInsets(top = 0),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        title = {
            Text(
                text = "Hi, Sachin",
                modifier = Modifier.padding(start = 8.dp),
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* Handle navigation icon click */ }) {
                AsyncImage(
                    model = "https://cdn-icons-png.flaticon.com/512/3135/3135715.png",
                    contentDescription = "",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    Icons.Rounded.Search,
                    modifier = Modifier.size(30.dp),
                    contentDescription = "Search",
                    tint = Color.Black
                )
            }

            IconButton(onClick = { onClick() }) {
                Box(modifier = Modifier) {
                    Icon(
                        Icons.Rounded.ShoppingCart,
                        contentDescription = "Filter",
                        tint = Color.Black,
                        modifier = Modifier.size(30.dp)
                    )

                    Badge(modifier = Modifier.align(Alignment.TopEnd)) {
                        Text(text = "${mainViewModel.cartList.size}")
                    }

                }

            }
        })


}