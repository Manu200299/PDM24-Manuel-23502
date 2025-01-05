package com.example.lojaonline.domain.use_case

import android.util.JsonToken
import android.util.Log
import com.example.lojaonline.data.local.SessionManager
import com.example.lojaonline.data.repository.UserRepositoryImpl
import com.example.lojaonline.domain.model.User
import com.example.lojaonline.domain.model.UserAdd
import com.example.lojaonline.domain.model.UserLogin

class GetUsersUseCase(private val repository: UserRepositoryImpl) {
    suspend operator fun invoke(): List<User>{
        val response = repository.getUsers()
        Log.d("UseCase", "Data received: $response")
        return repository.getUsers()
    }
}

class GetUserByIdUseCase(private val repository: UserRepositoryImpl){
    suspend operator fun invoke(userId: Int): User{
        val response = repository.getUserById(userId)
        Log.d("UseCase", "Data received: $response")
        return repository.getUserById(userId)
    }
}

class AddUserUseCase(private val repository: UserRepositoryImpl){
    suspend operator fun invoke(userAdd: UserAdd){
        repository.addUser(userAdd)
        Log.d("UseCase", "Creating user: $userAdd")
    }
}

class LoginUserUseCase(private val repository: UserRepositoryImpl) {
    suspend operator fun invoke(userLogin: UserLogin) {
        repository.loginUser(userLogin)
        Log.d("UseCase", "Logging in user: $userLogin")
    }
}
