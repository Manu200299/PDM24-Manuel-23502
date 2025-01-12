package com.example.api_ny_times

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.api_ny_times.presentation.coin_list.NewsDetailScreen
import com.example.api_ny_times.presentation.coin_list.NewsListScreen
import com.example.api_ny_times.presentation.coin_list.NewsListViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val newsListViewModel: NewsListViewModel = viewModel()
    val news = newsListViewModel.news.collectAsState().value

    NavHost(navController = navController, startDestination = "news_list") {
        composable("news_list") {
            NewsListScreen(
                onNewsClick = { url ->
                    val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
                    navController.navigate("news_detail/$encodedUrl")
                }
            )
        }
        composable(
            route = "news_detail/{newsUrl}",
            arguments = listOf(navArgument("newsUrl") { type = NavType.StringType })
        ) { backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("newsUrl")
            val newsUrl = encodedUrl?.let {
                java.net.URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
            }
            NewsDetailScreen(newsUrl = newsUrl, allNews = news)
        }
    }
}




@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DefaultPreview() {
    AppNavigation()
}

