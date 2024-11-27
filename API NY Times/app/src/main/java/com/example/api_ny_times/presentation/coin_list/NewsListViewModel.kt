package com.example.api_ny_times.presentation.coin_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_ny_times.data.remote.api.RetrofitInstance
import com.example.api_ny_times.data.repository.NewsRepositoryImpl
import com.example.api_ny_times.domain.model.News
import com.example.api_ny_times.domain.repository.NewsRepository
import com.example.api_ny_times.domain.use_case.GetNewsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NewsListViewModel: ViewModel() {
    private val api = RetrofitInstance.api
    private val repository = NewsRepositoryImpl(api)
    private val getNewsUseCase = GetNewsUseCase(repository)

    val news = MutableStateFlow<List<News>>(emptyList())

    fun fetchNews() {
        viewModelScope.launch {
            try {
                Log.d("NewsListViewModel", "Starting news fetch...")
                val fetchedNews = getNewsUseCase()
                if (fetchedNews.isEmpty()) {
                    Log.w("NewsListViewModel", "No news articles received")
                }
                news.value = fetchedNews
                Log.d("NewsListViewModel", "News fetched successfully: ${news.value.size} articles")
            } catch (e: Exception) {
                Log.e("NewsListViewModel", "Error fetching news: ${e.message}", e)
                news.value = emptyList()
            }
        }
    }

}