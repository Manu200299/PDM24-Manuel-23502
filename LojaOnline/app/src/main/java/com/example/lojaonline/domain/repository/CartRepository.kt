package com.example.lojaonline.domain.repository

import com.example.lojaonline.domain.model.AddToCart
import com.example.lojaonline.domain.model.CartItem

interface CartRepository {
    suspend fun addToCart(addToCart: AddToCart): CartItem
    suspend fun getCartFromUser(userId: Int): List<CartItem>
}