package com.sgtech.ecommerceui.model.api.product

import com.sgtech.ecommerceui.data.product.ProductsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductAPI {
    @GET("products")
    fun getProducts(): Call<List<ProductsItem>>
}

interface ProductDetailAPI {
    @GET("products/{id}")
    fun getProductDetail(@Path("id") id: Int): Call<ProductsItem>

}