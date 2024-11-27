package com.example.api_ny_times.data.remote.model

import android.content.ClipDescription
import android.media.Image
import com.example.api_ny_times.domain.model.News

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