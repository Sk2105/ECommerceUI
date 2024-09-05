package com.sgtech.ecommerceui.data.cart

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Cart(val id: Int, var isSelected: MutableState<Boolean> = mutableStateOf(false))