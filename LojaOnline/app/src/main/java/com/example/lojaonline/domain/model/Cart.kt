package com.example.lojaonline.domain.model

import com.example.lojaonline.data.remote.model.AddToCartDto
import com.example.lojaonline.data.remote.model.CartItemDto

data class CartItem(
    val userID: Int,
    val productID: Int,
    val quantity: Int,
    val isShared: Boolean,
    val productName: String,
){
    fun toCartItemDto(): CartItemDto{
        return CartItemDto(userID = userID, productID = productID, quantity = quantity, isShared = isShared, productName)

    }
}

data class AddToCart(
    val userID: Int,
    val productID: Int,
    val quantity: Int,
    val isShared: Boolean,
    val sharedToken: String
){
    fun toCartItemDto(): AddToCartDto{
        return AddToCartDto(userID = userID, productID = productID, quantity = quantity, isShared = isShared, sharedToken = sharedToken)

    }
}
