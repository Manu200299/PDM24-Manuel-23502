package com.example.lojaonline.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lojaonline.data.local.SessionManager
import com.example.lojaonline.data.remote.api.RetrofitInstance
import com.example.lojaonline.data.repository.UserRepositoryImpl
import com.example.lojaonline.domain.model.UserLogin
import com.example.lojaonline.domain.use_case.LoginUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginUserViewModel(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val api = RetrofitInstance.api
    private val repository = UserRepositoryImpl(api, sessionManager)
    private val loginUserUseCase = LoginUserUseCase(repository)

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun loginUser(loginUser: UserLogin) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val loginResponse = loginUserUseCase(loginUser)
//                sessionManager.saveUserToken(loginResponse.token)
//                sessionManager.saveUserId(loginResponse.userId)
                _loginState.value = LoginState.Success
                Log.d("UserLoginViewModel", "Logging in user: $loginUser")
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Unknown error")
                Log.e("UserLoginViewModel", "Error logging user in: ${e.message}")
            }
        }
    }

    val userToken: Flow<String?> = sessionManager.userToken

    class Factory(private val sessionManager: SessionManager) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginUserViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginUserViewModel(sessionManager) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}

