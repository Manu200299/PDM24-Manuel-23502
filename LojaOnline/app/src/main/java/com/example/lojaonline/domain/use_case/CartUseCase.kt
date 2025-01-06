package com.example.lojaonline.domain.use_case

import com.example.lojaonline.data.repository.CartRepositoryImpl
import com.example.lojaonline.domain.model.AddToCart
import com.example.lojaonline.domain.model.CartItem

class GetCartFromUserUseCase(private val repository: CartRepositoryImpl) {
    suspend operator fun invoke(userId: Int): List<CartItem> {
        return repository.getCartFromUser(userId)
    }
}

class AddToCartUseCase(private val repository: CartRepositoryImpl){
    suspend operator fun invoke(addToCart: AddToCart): CartItem{
        return repository.addToCart(addToCart)
    }
}