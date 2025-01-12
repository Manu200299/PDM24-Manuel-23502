package com.example.api_ny_times.domain.repository

import com.example.api_ny_times.domain.model.ArticlesList
import com.example.api_ny_times.domain.model.News

// App Rework (09/01/2025)

interface NewsRepository {
    suspend fun getNews(): List<ArticlesList>
}