package com.example.lojaonline.data.repository

import android.util.JsonToken
import android.util.Log
import com.example.lojaonline.data.local.SessionManager
import com.example.lojaonline.data.remote.api.LojaOnlineApi
import com.example.lojaonline.domain.model.User
import com.example.lojaonline.domain.model.UserAdd
import com.example.lojaonline.domain.model.UserLogin
import com.example.lojaonline.domain.model.UserLoginResponse
import com.example.lojaonline.domain.repository.UserRepository

class UserRepositoryImpl(
    private val api: LojaOnlineApi,
    private val sessionManager: SessionManager
    ): UserRepository {

    override suspend fun getUsers(): List<User> {
        val userDtos = api.getUsers()
        Log.d("UserRespositoryImpl", "API response: $userDtos")

        val users = userDtos.map { it.toUser() }
        Log.d("UserRepositoryImpl", "Mapped data: $users")

        return api.getUsers()
            .map { it.toUser()}
    }

    override suspend fun getUserById(userId: Int): User{
        val response = api.getUserById(userId)
        Log.d("UserRepositoryImpl", "API response: $response")
        if (response.isSuccessful){
            return response.body()?.toUser()
                ?: throw Exception("User profile not found")
        } else{
            throw Exception("Failed to fetch user profile")
        }
    }

    override  suspend fun addUser(addUser: UserAdd) {
        val addUserDto = addUser.toUserAddDto()
        val response = api.addUser(addUserDto)
        if (response.isSuccessful) {
            Log.d("UserRepositoryImpl", "User created successfully: $addUserDto")
        } else {
            val statusCode = response.code()
            val errorMessage = response.errorBody()?.string() ?: "Unknown error"
            Log.e("UserRepositoryImpl", "Failed to create user: HTTP $statusCode - $errorMessage")
            throw Exception("Failed to create user: HTTP $statusCode - $errorMessage")
        }
    }

    override suspend fun loginUser(loginUser: UserLogin): UserLoginResponse{
        val loginUserDto = loginUser.toUserLoginDto()
        val response = api.loginUser(loginUserDto)
        if (response.isSuccessful) {
            val loginResponse = response.body() ?: throw Exception("Empty response body")
            sessionManager.saveUserToken(loginResponse.token)
            sessionManager.saveUserId(loginResponse.userID)
            return loginResponse.toUserLoginResponse()
            Log.d("UserRepositoryImpl", "Logged in successfully and saved token: $loginUserDto | $loginResponse")
        } else {
            val statusCode = response.code()
            val errorMessage = response.errorBody()?.string() ?: "Unknown error"
            Log.e("UserRepositoryImpl", "Failed to create user: HTTP $statusCode - $errorMessage")
            throw Exception("Failed to create user: HTTP $statusCode - $errorMessage")
        }
    }
}