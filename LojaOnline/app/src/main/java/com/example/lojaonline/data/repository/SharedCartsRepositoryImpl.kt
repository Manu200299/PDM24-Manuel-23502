package com.example.lojaonline.data.repository

import com.example.lojaonline.data.remote.api.LojaOnlineApi
import com.example.lojaonline.data.remote.model.AddSharedCartRequestDto
import com.example.lojaonline.domain.repository.SharedCartRepository
import android.util.Log

class SharedCartRepositoryImpl(private val api: LojaOnlineApi) : SharedCartRepository {
    override suspend fun shareCart(userId: Int): String {
        val response = api.shareCart(userId)
        if (response.isSuccessful) {
            return response.body()?.shareCode ?: throw Exception("Share code is null")
        } else {
            Log.e("SharedCartRepository", "Failed to share cart: ${response.code()} ${response.message()}")
            Log.e("SharedCartRepository", "Error body: ${response.errorBody()?.string()}")
            throw Exception("Failed to share cart: ${response.code()} ${response.message()}")
        }
    }

    override suspend fun addSharedCart(userId: Int, shareCode: String) {
        val response = api.addSharedCart(AddSharedCartRequestDto(userId, shareCode))
        if (!response.isSuccessful) {
            Log.e("SharedCartRepository", "Failed to add shared cart: ${response.code()} ${response.message()}")
            Log.e("SharedCartRepository", "Error body: ${response.errorBody()?.string()}")
            throw Exception("Failed to add shared cart: ${response.code()} ${response.message()}")
        }
    }
}


