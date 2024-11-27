package com.example.api_ny_times.data.repository

import android.util.Log
import com.example.api_ny_times.data.remote.api.NyTimesApi
import com.example.api_ny_times.domain.model.News
import com.example.api_ny_times.domain.repository.NewsRepository


class NewsRepositoryImpl(private val api: NyTimesApi) : NewsRepository {
    override suspend fun getNews(): List<News> {
        return try {
            Log.d("NewsRepository", "Fetching news from API...")
            val response = api.getNews()
            Log.d("NewsRepository", "Raw API Response: $response")
            val articles = response.articles
            Log.d("NewsRepository", "Parsed articles: ${articles.size}")
            articles.map { it.toNews() }
        } catch (e: Exception) {
            Log.e("NewsRepository", "Error fetching news from API", e)
            emptyList()
        }
    }
}