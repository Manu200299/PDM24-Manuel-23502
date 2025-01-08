package com.example.api_ny_times.presentation.coin_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.api_ny_times.domain.model.News
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsDetailScreen(
    newsUrl: String?,
    allNews: List<News>,
    viewModel: NewsDetailViewModel = viewModel()
) {
    // Load news into the ViewModel
    LaunchedEffect(newsUrl) {
        if (newsUrl != null) {
            viewModel.loadNews(newsUrl, allNews)
        }
    }

    // Observe the selected news
    val news by viewModel.news.collectAsState()

    // Display the content
    if (news != null) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Title: ${news!!.title}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Published: ${news!!.publishedAt}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            GlideImage(
                model = news!!.urlToImage,
                contentDescription = "News Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "URL: ${news!!.url}", style = MaterialTheme.typography.bodyMedium)
        }
    } else {
        Text(text = "News item not found.", modifier = Modifier.fillMaxSize())
    }
}
