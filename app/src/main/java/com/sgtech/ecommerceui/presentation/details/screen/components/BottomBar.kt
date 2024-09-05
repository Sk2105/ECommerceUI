package com.sgtech.ecommerceui.presentation.details.screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddShoppingCart
import androidx.compose.material.icons.rounded.RemoveShoppingCart
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sgtech.ecommerceui.MainViewModel
import com.sgtech.ecommerceui.data.cart.Cart


@Preview(showBackground = true)
@Composable
fun BottomBar(
    productId: Int = 1,
    mainViewModel: MainViewModel = MainViewModel(),
    navHostController: NavHostController = rememberNavController()
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .padding(horizontal = 16.dp)
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = "$499.00",
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.25f),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
        IconButton(
            onClick = {
                if (mainViewModel.cartList.find { it.id == productId } != null) {
                    mainViewModel.cartList.removeIf() { it.id == productId }
                } else {
                    mainViewModel.cartList.add(Cart(productId))
                }
            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .weight(0.75f)
                .background(Color.Black, RoundedCornerShape(20.dp))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = if (mainViewModel.cartList.find { it.id == productId } != null) Icons.Rounded.RemoveShoppingCart else Icons.Rounded.AddShoppingCart,
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp),
                    tint = Color.White
                )
                Text(
                    text = if (mainViewModel.cartList.find { it.id == productId } != null) "Remove from cart" else "Add to cart",
                    modifier = Modifier.padding(end = 16.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }


        }

    }

}