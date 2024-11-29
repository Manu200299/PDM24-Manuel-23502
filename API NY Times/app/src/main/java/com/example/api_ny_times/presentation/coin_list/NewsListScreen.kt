package com.example.api_ny_times.presentation.coin_list

import android.graphics.Paint.Align
import android.os.Build
import android.util.Log
import android.widget.Space
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.api_ny_times.domain.model.News
import java.time.Instant
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsListScreen(
    viewModel: NewsListViewModel = viewModel(),
    onNewsClick: (String) -> Unit
) {
    val news by viewModel.news.collectAsState()
    val headerCustomColor = Color(0xFF0061b0)

    LaunchedEffect(Unit) {
        viewModel.fetchNews()
    }
    // Main Column
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .background(color = headerCustomColor)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Top Header Text
            Text(
                text = "Recent News",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall)
        }
        // Column that will lsit the news
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(news) { news ->
                Log.d("NewsListScreen", "Rendering article: ${news.title}")
                NewsItem(news = news, onClick ={ onNewsClick(news.url)})
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsItem(news: News, onClick: () -> Unit){
    var isClicked by remember { mutableStateOf(false) }

    // News List Card
    Card(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .background(Color.White)
            .padding(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ){
        // News List
        Column {
            // UrlToImage thumbnail
            GlideImage(
                model = news.urlToImage,
                contentDescription = "NewsThumbnail",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // News Title
                Text(
                    text = news.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
//                    Text( -> Not working due to "source" on JSON response
//                        text = news.name,
//                        style = MaterialTheme.typography.bodySmall,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant
//                    )

                    Text(
//                        text = "${ChronoUnit.MINUTES.between(news.publishedAt, Instant.now())} minutes ago",
                        text = "Published at ${news.publishedAt}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}