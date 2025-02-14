package com.example.lojaonline.domain.repository

import android.util.JsonToken
import com.example.lojaonline.data.local.SessionManager
import com.example.lojaonline.domain.model.User
import com.example.lojaonline.domain.model.UserAdd
import com.example.lojaonline.domain.model.UserLogin
import com.example.lojaonline.domain.model.UserLoginResponse

import retrofit2.Response
import retrofit2.http.Body

interface UserRepository{
    suspend fun getUsers(): List<User>
    suspend fun getUserById(userId: Int): User
    suspend fun addUser(@Body userAdd: UserAdd)
    suspend fun loginUser(@Body userLogin: UserLogin): UserLoginResponse
}
