package com.example.lojaonline.data.remote.model

import com.example.lojaonline.domain.model.User

data class UserDto(
    val userID: Int,
    val username: String,
    val email: String,
    val token: Int,
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
//    val token: Int,
    val password: String
)


// Dto model to add user
data class UserAddDto(
    val username: String,
    val email: String,
    val password: String,
    val createdAt: String,
    val updatedAt: String
)
