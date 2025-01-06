package com.example.lojaonline.data.repository

import android.util.Log
import com.example.lojaonline.data.remote.api.LojaOnlineApi
import com.example.lojaonline.data.remote.model.AddToCartDto
import com.example.lojaonline.domain.model.AddToCart
import com.example.lojaonline.domain.model.CartItem
import com.example.lojaonline.domain.repository.CartRepository

class CartRepositoryImpl(private val api: LojaOnlineApi): CartRepository {
    override suspend fun addToCart(addToCart: AddToCart): CartItem{
        val addCartItemDto = addToCart.toCartItemDto()
        val response = api.addCartItem(addCartItemDto)
        if (response.isSuccessful){
            return response.body()?.toCartItem() ?: throw Exception("Cart item response body is null")
            Log.d("CartRepositoryImpl", "Cart created successfully: $addCartItemDto")
        } else  {
            val statusCode = response.code()
            val errorMessage = response.errorBody()?.string() ?: "Unknown error"
            Log.e("CartRepositoryImpl", "Failed to create Cart: HTTP $statusCode - $errorMessage")
            throw Exception("Failed to create Cart: HTTP $statusCode - $errorMessage")        }
    }

    override suspend fun getCartFromUser(userId: Int): List<CartItem>{
        val response = api.getCartFromUser(userId)
        if (response.isSuccessful){
            return response.body()?.map { it.toCartItem() } ?: emptyList()
        } else{
            throw Exception("Failed to get cart: ${response.code()} | ${response.message()}")
        }
    }
}

// Implement add and getAll cart feature.
// v0.dev is saying the repository a bit wrong.
// Ask for same structure as mine