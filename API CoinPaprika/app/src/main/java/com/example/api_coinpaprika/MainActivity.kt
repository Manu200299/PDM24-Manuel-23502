package com.example.api_coinpaprika

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "coin_list") {
        composable("coin_list") {
            CoinListScreen(onCoinClick = { coinId ->
                navController.navigate("coin_detail/$coinId")
            })
        }
        composable("coin_detail/{coinId}") { backStackEntry ->
            val coinId = backStackEntry.arguments?.getString("coinId") ?: return@composable
            CoinDetailScreen(coinId = coinId)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppNavigation()
}
