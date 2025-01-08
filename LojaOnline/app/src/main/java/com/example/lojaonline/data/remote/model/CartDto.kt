package com.example.lojaonline.data.remote.model

import com.example.lojaonline.domain.model.AddToCart
import com.example.lojaonline.domain.model.CartItem

data class CartItemDto(
    val userID: Int,
    val productID: Int,
    val quantity: Int,
    val isShared: Boolean,
    val productName: String,
){
    fun toCartItem(): CartItem{
        return CartItem(userID = userID, productID = productID, quantity = quantity, isShared = isShared, productName = productName)
    }
}

data class AddToCartDto(
    val userID: Int,
    val productID: Int,
    val quantity: Int,
    val isShared: Boolean,
    val sharedToken: String
)


