package com.example.lojaonline.data.remote.model

import com.example.lojaonline.domain.model.AddToCart
import com.example.lojaonline.domain.model.CartItem

data class CartItemDto(
    val userID: Int,
    val productID: Int,
    val quantity: Int,
    val isShared: Boolean,
    val productName: String,
    val productPrice: Double
){
    fun toCartItem(): CartItem{
        return CartItem(userID = userID, productID = productID, quantity = quantity, isShared = isShared, productName = productName, productPrice = productPrice)
    }
}

data class AddToCartDto(
    val userID: Int,
    val productID: Int,
    val quantity: Int,
    val isShared: Boolean
)


