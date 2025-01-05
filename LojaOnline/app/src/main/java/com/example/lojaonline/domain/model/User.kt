package com.example.lojaonline.domain.model

import android.net.eap.EapSessionConfig.EapMethodConfig
import com.example.lojaonline.data.remote.model.UserAddDto
import com.example.lojaonline.data.remote.model.UserDto
import com.example.lojaonline.data.remote.model.UserLoginDto

data class User(
    val userID: Int,
    val username: String,
    val email: String,
    val token: String,
    val createdAt: String,
    val updatedAt: String,
    val password: String
)

// Model to add user
data class UserAdd(
    val username: String,
    val email: String,
    val createdAt: String,
    val updatedAt: String,
    val password: String
){
    fun toUserAddDto(): UserAddDto{
        return UserAddDto(username = username, email = email, password = password, createdAt = createdAt, updatedAt = updatedAt)
    }
}

// Model to login user
data class UserLogin(
    val username: String,
    val password: String,
) {
    fun toUserLoginDto(): UserLoginDto {
        return UserLoginDto(username = username, password = password)
    }
}

data class UserLoginResponse(
    val token: String
)
