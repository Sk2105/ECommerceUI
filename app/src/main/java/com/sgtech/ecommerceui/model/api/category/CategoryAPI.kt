package com.sgtech.ecommerceui.model.api.category

import retrofit2.Call
import retrofit2.http.GET

interface CategoryAPI {
    @GET("products/categories")
    fun getCategories(): Call<List<String>>
}