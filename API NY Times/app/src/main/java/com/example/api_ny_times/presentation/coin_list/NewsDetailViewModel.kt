package com.example.api_ny_times.presentation.coin_list

import androidx.lifecycle.ViewModel
import com.example.api_ny_times.domain.model.ArticlesList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewsDetailViewModel : ViewModel() {
    private val _article = MutableStateFlow<ArticlesList?>(null)
    val article: StateFlow<ArticlesList?> = _article.asStateFlow()

    fun loadNews(article: ArticlesList) {
        _article.value = article
    }
}
