package com.sgtech.ecommerceui.model.api.category.state

sealed class CategoriesResult {
    data object Loading : CategoriesResult()
    data class Success(val categories: List<String>) : CategoriesResult()
    data class Error(val message: String) : CategoriesResult()

}