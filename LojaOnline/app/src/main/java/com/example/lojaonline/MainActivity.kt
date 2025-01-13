package com.example.lojaonline

import com.example.lojaonline.presentation.register.RegisterUserScreen
import com.example.lojaonline.presentation.user.UserProfileScreen
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.lojaonline.presentation.login.LoginUserScreen
import com.example.lojaonline.data.local.SessionManager
import com.example.lojaonline.presentation.address.AddAddressScreen
import com.example.lojaonline.presentation.product.AddProductScreen
import com.example.lojaonline.presentation.cart.CartScreen
import com.example.lojaonline.presentation.order.OrderDetailsScreen
import com.example.lojaonline.presentation.order.OrderListScreen
import com.example.lojaonline.presentation.product.ProductListScreen
import com.example.lojaonline.presentation.sharedCart.SharedCartScreen
import com.example.lojaonline.ui.theme.LojaOnlneTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LojaOnlneTheme {
                val navController = rememberNavController()
                val sessionManager = remember { SessionManager(this@MainActivity) }
                var isLoggedIn by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    sessionManager.isLoggedIn.collect { loggedIn ->
                        isLoggedIn = loggedIn
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = if (isLoggedIn) "profile" else "login"
                ) {
                    composable("login") {
                        LoginUserScreen(
                            onLoginSuccess = {
                                navController.navigate("profile") {
                                    popUpTo("login") { inclusive = true }
                                }
                            },
                            onRegisterClick = {
                                navController.navigate("register"){
                                    popUpTo("login") {inclusive = true }
                                }
                            }
                        )
                    }
                    composable("profile") {
                        UserProfileScreen(
                            sessionManager = sessionManager,
                            onLogout = {
                                navController.navigate("login") {
                                    popUpTo("profile") { inclusive = true }
                                }
                            },
                            onAddAddressClick = {
                                navController.navigate("addAddress")
                            },
                            onProductsClick = {
                                navController.navigate("productList")
                            },
                            onAddProductsClick = {
                                navController.navigate("addProduct")
                            },
                            onViewOrdersClick = {
                                navController.navigate("orders")
                            }
                        )
                    }
                    composable("register"){
                        RegisterUserScreen(
                            sessionManager = sessionManager,
                            onRegisterSuccess = {
                                navController.navigate("login"){
                                    popUpTo("register") { inclusive = true }
                                }
                            },
                            onBackToLogin = {
                                navController.navigateUp()
                            }
                        )
                    }
                    composable("addAddress"){
                        AddAddressScreen(
                            sessionManager = sessionManager,
                            onAddressAdded = {
                                navController.navigateUp()
                            },
                        )
                    }
                    composable("productList") {
                        ProductListScreen(
                            onAddProductClick = {
                                navController.navigate("addProduct")
                            },
                            onViewCartClick = {
                                navController.navigate("cart")
                            },
                            sessionManager = sessionManager
                        )
                    }
                    composable("addProduct") {
                        AddProductScreen(
                            onProductAdded = {
                                navController.navigateUp()
                            },
                            sessionManager = sessionManager
                        )
                    }
                    composable("cart") {
                        CartScreen(
                            sessionManager = sessionManager,
                            onBackClick = {
                                navController.navigateUp()
                            },
                            onShareCartClick = {
                                navController.navigate("shareCart")
                            },
                            onAddSharedCartClick = {
                                navController.navigate("addSharedCart")
                            }
                        )
                    }
                    composable("orders"){
                        OrderListScreen(
                            sessionManager = sessionManager,
                            onOrderClick = {
                                navController.navigate("orderDetails") // orderDetail implementation
                            },
                            onBackClick = {
                                navController.navigateUp()
                            }
                        )
                    }
                    composable("orderDetails"){
                        OrderDetailsScreen(
                            sessionManager = sessionManager,
                            onBackClick = {
                                navController.navigateUp()
                            },
                            orderId = 2 // NEEDS TO FIX
                        )
                    }
                    composable("shareCart") {
                        SharedCartScreen(
                            sessionManager = sessionManager,
                            onNavigateBack = {
                                navController.navigateUp()
                            },
                        )
                    }
                    composable("addSharedCart") {
                        SharedCartScreen(
                            sessionManager = sessionManager,
                            onNavigateBack = {
                                navController.navigateUp()
                            },
                        )
                    }
                }
            }
        }
    }
}