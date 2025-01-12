package com.example.api_ny_times.data.remote.model

import android.content.ClipDescription
import android.media.Image
import com.example.api_ny_times.domain.model.ArticlesList
import com.example.api_ny_times.domain.model.News
import com.example.api_ny_times.domain.model.SourceList

data class NewsDto(
    val url: String?,
    val name: String?,
    val title: String?,
    val urlToImage: String?,
    val publishedAt: String?
) {
    fun toNews(): News {
        return News(
            url = url.orEmpty(),
            name = name.orEmpty(),
            title = title.orEmpty(),
            urlToImage = urlToImage.orEmpty(),
            publishedAt = publishedAt.orEmpty()
        )
    }
}


data class SourceDto(
    val name: String
)


// App Rework (09/01/2025)

data class NewsResponseDto(
    val status: String, // Usually ok
    val totalResults: Int,
    val articles: List<ArticlesListDto> // Lists the actual articles details
)

data class ArticlesListDto(
    val source: SourceListDto,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String, // Only thing closest to an ID
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
){
    fun toArticlesList(): ArticlesList{
        return ArticlesList(
            source = source.toSource(),
            author = author ?: "",
            title = title,
            description = description ?: "",
            url = url,
            urlToImage = urlToImage ?: "",
            publishedAt = publishedAt ?: "",
            content = content ?: ""

        )
    }
}

data class SourceListDto(
    val id: String?,
    val name: String
){
    fun toSource(): SourceList{
        return SourceList(
            id = id ?: "",
            name = name
        )
    }
}