package com.sgtech.ecommerceui.presentation.home.screen.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sgtech.ecommerceui.CategoriesUiState
import com.sgtech.ecommerceui.MainViewModel
import com.sgtech.ecommerceui.model.api.category.state.CategoriesResult


@Composable
fun CategorySection(mainViewModel: MainViewModel) {
    val currentSelection = "All"


    Log.d("response", mainViewModel.categoriesUiState.value.categoriesResult.toString())

    when (mainViewModel.categoriesUiState.value.categoriesResult) {
        is CategoriesResult.Error -> {
            Log.d("response", "Error")
            val error =
                (mainViewModel.categoriesUiState.value.categoriesResult as CategoriesResult.Error).message
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                contentAlignment = Alignment.Center
            )
            {
                Text(text = error)
            }

        }

        CategoriesResult.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                contentAlignment = Alignment.Center
            )
            {
                CircularProgressIndicator(color = Color.Black)
            }
        }

        is CategoriesResult.Success -> {
            Log.d("response", "Success")
            val categoryList =
                (mainViewModel.categoriesUiState.value.categoriesResult as CategoriesResult.Success).categories.toMutableList()

            categoryList.add(0, "All")
            LazyRow(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(categoryList.size) { idx ->
                    CategoryItem(
                        category = categoryList[idx],
                        isSelected = mainViewModel.constraint.value == categoryList[idx]
                    ) {
                        mainViewModel.constraint.value = categoryList[idx]
                    }
                }


            }

        }

        null -> TODO()
    }


}

@Composable
fun CategoryItem(
    category: String = "All",
    isSelected: Boolean = false,
    onClick: (String) -> Unit = {}
) {
    SuggestionChip(
        onClick = {
            onClick(category)
        },
        label = {
            Text(text = category)
        },
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        colors = SuggestionChipDefaults.suggestionChipColors(

            containerColor = if (isSelected) Color.Black else Color.White,
            disabledContainerColor = Color.White.copy(0.7f),
            labelColor = if (isSelected) Color.White else Color.Black.copy(0.7f),
            disabledLabelColor = Color.Gray,
        ),

        border = null
    )
}