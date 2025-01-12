package com.example.api_ny_times

import android.net.Uri
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
            val newsListViewModel: NewsListViewModel = viewModel()
            AppNavigation(newsListViewModel)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(newsListViewModel: NewsListViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "news_list") {
        composable("news_list") {
            NewsListScreen(
                viewModel = newsListViewModel,
                onNewsClick = { article ->
                    val encodedUrl = Uri.encode(article.url)
                    navController.navigate("news_detail/$encodedUrl")
                }
            )
        }
        composable(
            route = "news_detail/{encodedUrl}",
            arguments = listOf(navArgument("encodedUrl") { type = NavType.StringType })
        ) { backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("encodedUrl")
            val decodedUrl = encodedUrl?.let { Uri.decode(it) }
            decodedUrl?.let { url ->
                newsListViewModel.getArticleByUrl(url)?.let { article ->
                    NewsDetailScreen(article = article)
                }
            }
        }
    }
}

