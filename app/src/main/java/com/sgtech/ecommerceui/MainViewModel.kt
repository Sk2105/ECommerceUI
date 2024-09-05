package com.sgtech.ecommerceui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sgtech.ecommerceui.data.cart.Cart
import com.sgtech.ecommerceui.model.api.RetrofitModel
import com.sgtech.ecommerceui.model.api.category.state.CategoriesResult
import com.sgtech.ecommerceui.model.api.product.state.ProductDetailResult
import com.sgtech.ecommerceui.model.api.product.state.ProductResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _uiState = mutableStateOf(MainUiState())
    val uiState = _uiState
    val productUiState = mutableStateOf(ProductUiState())
    private val _categoriesUiState = mutableStateOf(CategoriesUiState())
    val categoriesUiState = _categoriesUiState
    val constraint = mutableStateOf("All")
    val cartList = mutableStateListOf<Cart>()


    init {
        fetchCategories()
        fetchProducts()
    }

    private fun fetchCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            RetrofitModel.fetchCategories().catch {
                _categoriesUiState.value =
                    CategoriesUiState(categoriesResult = CategoriesResult.Error(it.message.toString()))
            }.collect { res ->
                _categoriesUiState.value = CategoriesUiState(categoriesResult = res)
            }
        }
    }

    private fun fetchProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            RetrofitModel.fetchProducts().catch {
                _uiState.value =
                    MainUiState(productResult = ProductResult.Error(it.message.toString()))

            }.collect { res ->
                _uiState.value = MainUiState(productResult = res)
            }
        }
    }

    suspend fun fetchProductById(id: Int) {
        productUiState.value = ProductUiState(productDetailResult = ProductDetailResult.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            RetrofitModel.fetchProductDetail(id).catch {
                productUiState.value =
                    ProductUiState(productDetailResult = ProductDetailResult.Error(it.message.toString()))
            }.collect { res ->
                productUiState.value = ProductUiState(productDetailResult = res)
            }
        }
    }
}

data class MainUiState(
    val productResult: ProductResult = ProductResult.Loading
)

data class CategoriesUiState(
    val categoriesResult: CategoriesResult = CategoriesResult.Loading
)

data class ProductUiState(
    val productDetailResult: ProductDetailResult = ProductDetailResult.Loading
)