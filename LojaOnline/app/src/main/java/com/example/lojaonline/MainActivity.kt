package com.example.lojaonline

import RegisterUserScreen
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.lojaonline.presentation.LoginUserScreen
import com.example.lojaonline.presentation.UserProfileScreen
import com.example.lojaonline.data.local.SessionManager
import com.example.lojaonline.presentation.AddAddressScreen
import com.example.lojaonline.ui.theme.LojaOnlneTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
                }
            }
        }
    }
}