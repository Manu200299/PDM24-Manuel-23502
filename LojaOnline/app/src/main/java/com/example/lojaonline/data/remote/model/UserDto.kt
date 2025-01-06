package com.example.lojaonline.data.remote.model

import com.example.lojaonline.domain.model.User
import com.example.lojaonline.domain.model.UserLoginResponse

data class UserDto(
    val userID: Int,
    val username: String,
    val email: String,
    val token: String,
    val createdAt: String,
    val updatedAt: String,
    val password: String
) {
    fun toUser(): User {
        return User(userID = userID, username = username, email = email, token = token, createdAt = createdAt, updatedAt = updatedAt, password = password)
    }
}

// Dto model to login user
data class UserLoginDto(
    val username: String,
    val password: String
)

data class UserLoginResponseDto(
    val userID: Int,
    val token: String
){
    fun toUserLoginResponse(): UserLoginResponse{
        return UserLoginResponse(userID = userID, token =token)
    }
}


// Dto model to add user
data class UserAddDto(
    val username: String,
    val email: String,
    val token: String,
    val password: String,
    val createdAt: String,
    val updatedAt: String
)
