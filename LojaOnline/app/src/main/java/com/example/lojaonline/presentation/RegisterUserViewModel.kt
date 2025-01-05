package com.example.lojaonline.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lojaonline.data.local.SessionManager
import com.example.lojaonline.data.remote.api.RetrofitInstance
import com.example.lojaonline.data.repository.UserRepositoryImpl
import com.example.lojaonline.domain.model.UserAdd
import com.example.lojaonline.domain.use_case.AddUserUseCase
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class RegisterUserViewModel(
    private val sessionManager: SessionManager
) : ViewModel() {
    private val api = RetrofitInstance.api
    private val repository = UserRepositoryImpl(api, sessionManager)
    private val addUserUseCase = AddUserUseCase(repository)

    fun addUser(userAdd: UserAdd){
        viewModelScope.launch {
            try{
                Log.d("UserAddViewModel", "Adding user: $userAdd")
                addUserUseCase(userAdd)
            } catch (e: Exception){
                Log.e("ViewModel", "Error adding user: ${e.message}")
            }
        }
    }
}