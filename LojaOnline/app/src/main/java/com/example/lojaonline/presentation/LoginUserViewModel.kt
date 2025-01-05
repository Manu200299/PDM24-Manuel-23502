package com.example.lojaonline.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lojaonline.data.remote.api.RetrofitInstance
import com.example.lojaonline.data.repository.UserRepositoryImpl
import com.example.lojaonline.domain.model.UserLogin
import com.example.lojaonline.domain.use_case.LoginUserUseCase
import kotlinx.coroutines.launch

class LoginUserViewModel : ViewModel() {
    private val api = RetrofitInstance.api
    private val repository = UserRepositoryImpl(api)
    private val loginUserUseCase = LoginUserUseCase(repository)

    fun loginUser(loginUser: UserLogin){
        viewModelScope.launch {
            try{
                loginUserUseCase(loginUser)
                Log.d("UserLoginViewModel", "Logging in user: ${loginUser}")
            } catch (e:Exception){
                Log.e("UserLoginViewModel", "Error adding user: ${e.message}")
            }
        }
    }
}