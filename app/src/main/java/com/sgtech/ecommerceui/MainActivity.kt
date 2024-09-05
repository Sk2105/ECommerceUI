package com.sgtech.ecommerceui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.sgtech.ecommerceui.presentation.navigation.NavigationHost
import com.sgtech.ecommerceui.presentation.navigation.routes.AppRoute
import com.sgtech.ecommerceui.ui.theme.ECommerceUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ECommerceUITheme() {
                val mainViewModel: MainViewModel = viewModel()
                val navController = rememberNavController()
                NavigationHost(navController, mainViewModel)
            }
        }
    }
}