package com.example.lojaonline.domain.repository

import com.example.lojaonline.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface SharedCartRepository {
    suspend fun shareCart(userId: Int): String
    suspend fun addSharedCart(userId: Int, shareCode: String)
}
