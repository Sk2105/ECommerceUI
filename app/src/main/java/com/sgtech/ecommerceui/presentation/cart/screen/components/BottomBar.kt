package com.sgtech.ecommerceui.presentation.cart.screen.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sgtech.ecommerceui.MainViewModel
import com.sgtech.ecommerceui.model.api.product.state.ProductResult


@Preview(showBackground = true)
@Composable
fun BottomBar(
    mainViewModel: MainViewModel = MainViewModel(),
    navHostController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
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
            text = "$${calculateTotal(mainViewModel)}",
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.25f),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
        IconButton(
            onClick = {
                Toast.makeText(context, "Proceed to Checkout", Toast.LENGTH_SHORT).show()

            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .weight(0.75f)
                .background(Color.Black, RoundedCornerShape(20.dp))
        ) {

            Text(
                text = "Checkout",
                modifier = Modifier.padding(end = 16.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.White
            )


        }

    }

}

fun calculateTotal(mainViewModel: MainViewModel): Double {
    var total = 0.0
    (mainViewModel.uiState.value.productResult as ProductResult.Success).products?.forEach { product ->
        if (mainViewModel.cartList.find { it.id == product.id }?.isSelected?.value == true) {
            total += product.price
        }
    }
    return total
}