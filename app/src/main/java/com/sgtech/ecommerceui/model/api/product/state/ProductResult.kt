package com.sgtech.ecommerceui.model.api.product.state

import com.sgtech.ecommerceui.data.product.ProductsItem

sealed class ProductResult {
    data object Loading : ProductResult()
    data class Success(val products: List<ProductsItem>) : ProductResult()
    data class Error(val message: String) : ProductResult()
}