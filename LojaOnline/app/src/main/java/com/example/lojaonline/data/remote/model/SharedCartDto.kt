package com.example.lojaonline.data.remote.model

import kotlinx.coroutines.flow.Flow

data class SharedCartDto(
    val sharedCartId: Int,
    val ownerUserId: Int,
    val shareCode: String,
    val createdAt: String,
    val expiresAt: String
)

data class CreateSharedCartResponseDto(
    val shareCode: String
)

data class AddSharedCartRequestDto(
    val userId: Int,
    val shareCode: String
)