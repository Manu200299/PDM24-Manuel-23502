package com.example.api_ny_times.domain.model

import retrofit2.http.Url

data class News(
    val url: String,
    val name: String,
    val title: String,
    val urlToImage: String,
    val publishedAt: String
)
