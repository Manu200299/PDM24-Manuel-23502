package com.example.api_ny_times.presentation.coin_list

import androidx.lifecycle.ViewModel
import com.example.api_ny_times.domain.model.News
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NewsDetailViewModel : ViewModel() {
    private val _news = MutableStateFlow<News?>(null)
    val news: StateFlow<News?> = _news

    fun loadNews(newsUrl: String, allNews: List<News>) {
        _news.value = allNews.find { it.url == newsUrl }
    }
}
