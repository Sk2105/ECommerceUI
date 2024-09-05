package com.sgtech.ecommerceui.model.api

import android.util.Log
import com.sgtech.ecommerceui.model.api.category.CategoryAPI
import com.sgtech.ecommerceui.model.api.category.state.CategoriesResult
import com.sgtech.ecommerceui.model.api.product.ProductAPI
import com.sgtech.ecommerceui.model.api.product.ProductDetailAPI
import com.sgtech.ecommerceui.model.api.product.state.ProductDetailResult
import com.sgtech.ecommerceui.model.api.product.state.ProductResult
import com.sgtech.ecommerceui.utils.baseUrl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitModel {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val productResponse = retrofit.create(ProductAPI::class.java)
    private val categoryResponse = retrofit.create(CategoryAPI::class.java)
    private val productByIdAPI = retrofit.create(ProductDetailAPI::class.java)


    fun fetchProducts(): Flow<ProductResult> {
        Log.d("response", "fetchProducts")
        return flow {
            emit(ProductResult.Loading)
            val response = productResponse.getProducts().execute()
            if (response.isSuccessful) {
                val body = response.body()
                Log.d("response", body.toString())
                emit(body?.let { ProductResult.Success(it) }
                    ?: ProductResult.Error("Something went wrong"))
            } else {
                emit(ProductResult.Error(response.message()))
            }


        }
    }


    fun fetchCategories(): Flow<CategoriesResult> {
        Log.d("response", "fetchCategories")
        return flow {
            try {
                (CategoriesResult.Loading)
                val response = categoryResponse.getCategories().execute()

                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("response", body.toString())
                    emit(body?.let {
                        CategoriesResult.Success(it)
                    }
                        ?: CategoriesResult.Error("Something went wrong"))
                } else {
                    emit(CategoriesResult.Error(response.message()))
                }
            } catch (error: Exception) {
                emit(CategoriesResult.Error(error.message.toString()))
            }


        }
    }


    fun fetchProductDetail(id: Int): Flow<ProductDetailResult> {
        Log.d("response", "fetchProductById")
        return flow {
            emit(ProductDetailResult.Loading)
            val response = productByIdAPI.getProductDetail(id).execute()
            if (response.isSuccessful) {
                val body = response.body()
                Log.d("response", body.toString())
                emit(body?.let { ProductDetailResult.Success(it) }
                    ?: ProductDetailResult.Error("Something went wrong"))
            } else {
                emit(ProductDetailResult.Error(response.message()))
            }
        }
    }


}