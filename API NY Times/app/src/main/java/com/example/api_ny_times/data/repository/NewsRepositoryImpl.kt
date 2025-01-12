package com.example.api_ny_times.data.repository

import android.util.Log
import com.example.api_ny_times.data.remote.api.NewsApi
import com.example.api_ny_times.data.remote.api.NewsResponse
import com.example.api_ny_times.domain.model.ArticlesList
import com.example.api_ny_times.domain.model.News
import com.example.api_ny_times.domain.repository.NewsRepository


//class NewsRepositoryImpl(private val api: NyTimesApi) : NewsRepository {
//    override suspend fun getNews(): List<News> {
//        return try {
//            Log.d("NewsRepository", "Fetching news from API...")
//            val response = api.getNews()
//            Log.d("NewsRepository", "Raw API Response: $response")
//            val articles = response.articles
//            Log.d("NewsRepository", "Parsed articles: ${articles.size}")
//            articles.map { it.toNews() }
//        } catch (e: Exception) {
//            Log.e("NewsRepository", "Error fetching news from API", e)
//            emptyList()
//        }
//    }
//}

// App Rework (09/01/2025)

class NewsRepositoryImpl(private val api: NewsApi): NewsRepository {
    override suspend fun getNews(): List<ArticlesList> {
        return try{
            Log.d("NewsRepository", "Fetching news from API...")
            val response = api.getNews()
            val articles = response.articles
            Log.d("NewsRepository", "Raw API Response: $response")
            val totalResults = response.totalResults
            Log.d("NewsRepository", "Total articles: $totalResults")
            articles.map { it.toArticlesList() }
        } catch (e :Exception){
            Log.e("NewsRepository", "Error fetching news from API", e)
            emptyList()
        }
    }
}