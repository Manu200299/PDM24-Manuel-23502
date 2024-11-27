package com.example.api_ny_times.domain.use_case

import com.example.api_ny_times.domain.model.News
import com.example.api_ny_times.domain.repository.NewsRepository

class GetNewsUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(): List<News> {
        return repository.getNews()
    }
}