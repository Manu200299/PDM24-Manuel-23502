package com.example.api_ny_times.data.remote.api

import android.provider.MediaStore.Audio.Artists
import com.bumptech.glide.integration.ktx.Status
import com.example.api_ny_times.data.remote.model.ArticlesListDto
import com.example.api_ny_times.data.remote.model.NewsDto
import com.example.api_ny_times.data.remote.model.NewsResponseDto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// API BEING USED IS NOT NYTIMES BUT NEWSAPI!!
const val apiKey = "ffcf6c2cb8c743669c8b3e30b5fa6328"

object RetrofitInstance {
    val api: NewsApi by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)

    }
}

interface NyTimesApi {
    @GET("v2/top-headlines?country=us&apiKey=ffcf6c2cb8c743669c8b3e30b5fa6328")
    suspend fun getNews(): NewsResponse
}

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsDto>
)

// App Rework 09/01/2025

interface NewsApi {
    @GET("v2/top-headlines?country=us&apiKey=ffcf6c2cb8c743669c8b3e30b5fa6328")
    suspend fun getNews(): NewsResponseDto
}
