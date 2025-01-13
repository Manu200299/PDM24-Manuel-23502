package com.example.lojaonline.data.repository

import com.example.lojaonline.data.remote.api.LojaOnlineApi
import com.example.lojaonline.data.remote.model.AddSharedCartRequestDto
import com.example.lojaonline.domain.model.CartItem
import com.example.lojaonline.domain.repository.SharedCartRepository
import kotlinx.coroutines.flow.Flow


class SharedCartRepositoryImpl(private val api: LojaOnlineApi) : SharedCartRepository {
    override suspend fun shareCart(userId: Flow<Int?>): String {
        val response = api.shareCart(userId)
        if (response.isSuccessful) {
            return response.body()?.shareCode ?: throw Exception("Share code is null")
        } else {
            throw Exception("Failed to share cart: ${response.code()} ${response.message()}")
        }
    }

    override suspend fun getSharedCart(shareCode: String): List<CartItem> {
        val response = api.getSharedCart(shareCode)
        if (response.isSuccessful) {
            return response.body()?.map { it.toCartItem() } ?: emptyList()
        } else {
            throw Exception("Failed to get shared cart: ${response.code()} ${response.message()}")
        }
    }

    override suspend fun addSharedCart(userId: Flow<Int?>, shareCode: String) {
        val response = api.addSharedCart(AddSharedCartRequestDto(userId, shareCode))
        if (!response.isSuccessful) {
            throw Exception("Failed to add shared cart: ${response.code()} ${response.message()}")
        }
    }
}