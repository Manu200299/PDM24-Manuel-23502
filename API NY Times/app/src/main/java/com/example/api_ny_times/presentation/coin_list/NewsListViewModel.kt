package com.example.api_ny_times.presentation.coin_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_ny_times.data.remote.api.RetrofitInstance
import com.example.api_ny_times.data.repository.NewsRepositoryImpl
import com.example.api_ny_times.domain.model.ArticlesList
import com.example.api_ny_times.domain.model.News
import com.example.api_ny_times.domain.repository.NewsRepository
import com.example.api_ny_times.domain.use_case.GetNewsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsListViewModel: ViewModel() {
    private val api = RetrofitInstance.api
    private val repository = NewsRepositoryImpl(api)
    private val getNewsUseCase = GetNewsUseCase(repository)

    private val _news = MutableStateFlow<List<ArticlesList>>(emptyList())
    val news: StateFlow<List<ArticlesList>> = _news.asStateFlow()

    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Initial)
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    fun fetchNews() {
        viewModelScope.launch {
            _uiState.value = NewsUiState.Loading
            try {
                Log.d("NewsListViewModel", "Starting news fetch...")
                val fetchedNews = getNewsUseCase()
                if (fetchedNews.isEmpty()) {
                    Log.w("NewsListViewModel", "No news articles received")
                }
                _news.value = fetchedNews
                _uiState.value = NewsUiState.Success
                Log.d("NewsListViewModel", "News fetched successfully: ${_news.value.size} articles")
            } catch (e: Exception) {
                Log.e("NewsListViewModel", "Error fetching news: ${e.message}", e)
                _news.value = emptyList()
                _uiState.value = NewsUiState.Error(e.message ?: "An unkown error occurred")
            }
        }
    }

    fun getArticleByUrl(url: String): ArticlesList? {
        return _news.value.find { it.url == url }
    }
}

sealed class NewsUiState {
    object Initial : NewsUiState()
    object Loading : NewsUiState()
    object Success : NewsUiState()
    data class Error(val message: String) : NewsUiState()
}