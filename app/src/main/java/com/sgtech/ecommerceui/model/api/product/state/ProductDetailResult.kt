package com.sgtech.ecommerceui.model.api.product.state

import com.sgtech.ecommerceui.data.product.ProductsItem

sealed class ProductDetailResult {
    data object Loading : ProductDetailResult()
    data class Success(val products: ProductsItem) : ProductDetailResult()
    data class Error(val message: String) : ProductDetailResult()

}