package com.example.api_ny_times.domain.repository

import com.example.api_ny_times.domain.model.News

interface NewsRepository {
    suspend fun getNews(): List<News>
}