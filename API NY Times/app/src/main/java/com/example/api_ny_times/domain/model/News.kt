package com.example.api_ny_times.domain.model

data class News(
    val url: String,
    val name: String,
    val title: String,
    val urlToImage: String,
    val publishedAt: String
)

// App Rework (09/01/2025)

// I think its not needed since domain layer does not interact directly with api
data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticlesList>
)

data class ArticlesList(
    val source: SourceList,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String,
)

data class SourceList(
    val id: String?,
    val name: String
)