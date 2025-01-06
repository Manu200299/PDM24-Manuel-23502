package com.example.lojaonline.domain.model

import com.example.lojaonline.data.remote.model.AddToCartDto
import com.example.lojaonline.data.remote.model.CartItemDto

data class CartItem(
    val userID: Int,
    val productID: Int,
    val quantity: Int,
    val isShared: Boolean,
    val productName: String,
    val productPrice: Double
){
    fun toCartItemDto(): CartItemDto{
        return CartItemDto(userID = userID, productID = productID, quantity = quantity, isShared = isShared, productName, productPrice)

    }
}

data class AddToCart(
    val userID: Int,
    val productID: Int,
    val quantity: Int,
    val isShared: Boolean
){
    fun toCartItemDto(): AddToCartDto{
        return AddToCartDto(userID = userID, productID = productID, quantity = quantity, isShared = isShared)

    }
}
