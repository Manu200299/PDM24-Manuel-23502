package com.example.lojaonline.domain.use_case

import com.example.lojaonline.domain.model.CartItem
import com.example.lojaonline.domain.repository.SharedCartRepository
import kotlinx.coroutines.flow.Flow

class ShareCartUseCase(private val repository: SharedCartRepository) {
    suspend operator fun invoke(userId: Int): String {
        return repository.shareCart(userId)
    }
}

class AddSharedCartUseCase(private val repository: SharedCartRepository) {
    suspend operator fun invoke(userId: Int, shareCode: String) {
        repository.addSharedCart(userId, shareCode)
    }
}